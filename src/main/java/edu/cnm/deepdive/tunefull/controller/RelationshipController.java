package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.service.RelationshipService;
import edu.cnm.deepdive.tunefull.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Relationship controller provides endpoints to determine and update the relationship between users.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
 * @version 1.0
 * @since 1.0
 */
@RestController
//@RequestMapping("/relationships")
@ExposesResourceFor(Relationship.class)
public class RelationshipController {

  private final RelationshipService relationshipService;
  private final UserService userService;

  public RelationshipController(
      RelationshipService relationshipService,
      UserService userService) {
    this.relationshipService = relationshipService;
    this.userService = userService;
  }

  /**
   * /relationships/friendships: gets all relationships in which the user is a friend
   *
   * @param auth- Authentication type
   * @return list of relationships the user is a friend of
   */
  @GetMapping(value = "/friendships", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFriends(Authentication auth) {
    return relationshipService.getFriendships((User) auth.getPrincipal());
  }

  /**
   * /relationships/follows: gets all relationships in which the user is following someone
   *
   * @param auth- Authentication type
   * @return list of relationships in which the current user is following another user
   */
  @GetMapping(value = "/follows", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFollows(Authentication auth) {
    return relationshipService.getFollows((User) auth.getPrincipal());
  }

  /**
   * /relationships/unaccepted: gets all relationships in which the user has
   * received a friend request and hasn't responded yet
   *
   * @param auth- Authentication type
   * @return list of relationships in which the user hasn't responded to yet.
   */
  @GetMapping(value = "/unaccepted", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myUnacceptedRequests(Authentication auth) {
    return relationshipService.getUnaccepted((User) auth.getPrincipal());
  }

  /**
   * /relationship: send friend request
   *
   * @param auth- Authentication type
   * @param user- User type
   * @return A new relationship between two users
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Relationship requestFriendship(Authentication auth,
      @RequestBody User user) {
    return userService.get(user.getId())
        .map((requested) -> relationshipService.requestFriendship((User) auth.getPrincipal(), requested))
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * /relationships: start following another user
   *
   * @param auth- Authentication type
   * @param user- User type
   * @return A relationship following another user
   */
  @PostMapping(value = "/follows", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Relationship startFollowing(Authentication auth,
      @RequestBody User user) {
    return userService.get(user.getId())
        .map((followed) -> relationshipService.startFollowing((User) auth.getPrincipal(), followed))
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * /relationships: updates a relationship between two users
   *
   * @param auth- Authentication type
   * @param relationshipId- long type
   * @param accepted- boolean type
   * @return A relationship between two friends.
   */
  @PutMapping(value = "/friendships/{relationshipId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
      MediaType.APPLICATION_JSON_VALUE)
  public boolean setFriendshipAccepted(Authentication auth,
      @PathVariable long relationshipId, @RequestBody boolean accepted) {
    return relationshipService.get(relationshipId)
        .map((friendship) -> relationshipService.setFriendshipAccepted(friendship, accepted))
        .orElseThrow(NoSuchElementException::new);
  }

}
