package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.service.RelationshipService;
import java.util.List;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relationships")
@ExposesResourceFor(Relationship.class)
public class RelationshipController {

  private final RelationshipService relationshipService;

  public RelationshipController(
      RelationshipService relationshipService) {
    this.relationshipService = relationshipService;
  }

  // /relationships/friendships: gets all relationships in which the user is a friend
  @GetMapping(value = "/friendships", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFriends(Authentication auth) {
    return null;
  }

  // /relationships/follows: gets all relationships in which the user is following someone
  @GetMapping(value = "/follows", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myFollows(Authentication auth) {
    return null;
  }

  // /relationships/unaccepted: gets all relationships in which the user has
  // received a friend request and hasn't responded yet
  @GetMapping(value = "/unaccepted", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Relationship> myUnacceptedRequests(Authentication auth) {
    return null;
  }

  // /relationships: creates a relationship between two users
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Relationship createRelationship(Authentication auth) {
    return null;
  }

  // /relationships: updates a relationship between two users
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
      MediaType.APPLICATION_JSON_VALUE)
  public Relationship updateRelationship(Authentication auth) {
    return null;
  }

}
