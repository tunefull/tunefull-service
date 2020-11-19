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
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code RelationshipController} provides endpoints which allow the client to access data relating
 * to {@link Relationship}.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@RestController
@ExposesResourceFor(Relationship.class)
public class RelationshipController {

  private final RelationshipService relationshipService;
  private final UserService userService;

  /**
   * Autowired constructor for {@code RelationshipController}.
   *
   * @param relationshipService RelationshipService
   * @param userService         UserService
   */
  public RelationshipController(RelationshipService relationshipService, UserService userService) {
    this.relationshipService = relationshipService;
    this.userService = userService;
  }

  /**
   * Gets all relationships in which the current user is a friend.
   *
   * @param auth Authentication
   * @return List&ltRelationship&gt
   */
  @GetMapping(value = "/friendships", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFriends(Authentication auth) {
    return relationshipService.getFriendships((User) auth.getPrincipal());
  }

  /**
   * Gets all relationships in which the current user is following another user.
   *
   * @param auth Authentication
   * @return List&ltRelationship&gt
   */
  @GetMapping(value = "/follows", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFollows(Authentication auth) {
    return relationshipService.getFollows((User) auth.getPrincipal());
  }

  /**
   * Gets all relationships in which the user has received a friend request and hasn't yet
   * responded.
   *
   * @param auth Authentication
   * @return List&ltRelationship&gt
   */
  @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myPendingRequests(Authentication auth) {
    return relationshipService.getPending((User) auth.getPrincipal());
  }

  /**
   * Creates a relationship between the current user and another user. This relationship is not yet
   * a friendship, but is a follow and a friend request rolled into one.
   *
   * @param auth Authentication
   * @param user User
   * @return a new relationship between two users.
   */
  @PostMapping(value = "/friendships", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Relationship requestFriendship(Authentication auth,
      @RequestBody User user) {
    return userService.get(user.getId())
        .map((requested) -> relationshipService
            .requestFriendship((User) auth.getPrincipal(), requested))
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * Lets the current user start following another user.
   *
   * @param auth Authentication
   * @param user User
   * @return a new relationship following another user
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
   * Updates a relationship between two users.
   *
   * @param auth           Authentication
   * @param relationshipId long
   * @param accepted       boolean
   * @return the updated relationship
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
