package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.service.RelationshipService;
import java.util.List;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/relationships")
@ExposesResourceFor(Relationship.class)
public class RelationshipController {

  private final RelationshipService relationshipService;

  public RelationshipController(
      RelationshipService relationshipService) {
    this.relationshipService = relationshipService;
  }

  /**
   * /relationships/friendships: gets all relationships in which the user is a friend
   *
   * @param auth- Authentication type
   * @return list of relationship
   */
  @GetMapping(value = "/friendships", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFriends(Authentication auth) {
    return relationshipService.getFriendships((User) auth.getPrincipal());
  }

  /**
   * /relationships/follows: gets all relationships in which the user is following someone
   *
   * @param auth- Authentication type
   * @return list of relationship
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
   * @return list of relationship
   */
  @GetMapping(value = "/unaccepted", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myUnacceptedRequests(Authentication auth) {
    return relationshipService.getUnaccepted((User) auth.getPrincipal());
  }

  /**
   * /relationships: creates a relationship between two users
   *
   * @param auth- Authentication type
   * @param relationship- Relationship type
   * @return list of relationship
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Relationship createRelationship(Authentication auth,
      @RequestBody Relationship relationship) {
    return relationshipService.post(relationship);
  }

  /**
   * /relationships: updates a relationship between two users
   *
   * @param auth- Authentication type
   * @param relationship- Relationship type
   * @return list of relationship
   */
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
      MediaType.APPLICATION_JSON_VALUE)
  public Relationship updateRelationship(Authentication auth,
      @RequestBody Relationship relationship) {
    User user = (User) auth.getPrincipal();
    if (relationship.getRequester() == user || relationship.getRequested() == user) {
      return relationshipService.update(relationship);
    } else {
      return null;
    }
  }

}
