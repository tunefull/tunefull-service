package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * User entity holds the data model for User data and functions in the TuneFull server.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
 * @version 1.0
 * @since 1.0
 */
@Service
public class RelationshipService {

  private final RelationshipRepository relationshipRepository;

  public RelationshipService(RelationshipRepository relationshipRepository) {
    this.relationshipRepository = relationshipRepository;
  }

  public Optional<Relationship> get(long id) {
    return relationshipRepository.findById(id);
  }

  public List<Relationship> getFriendships(User user) {
    return relationshipRepository
        .getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(user, user);
  }

  public List<Relationship> getFollows(User user) {
    return relationshipRepository.getAllByRequesterAndFriendRelationshipFalse(user);
  }

  public List<Relationship> getUnaccepted(User user) {
    return relationshipRepository.getAllByRequestedAndFriendAcceptedNull(user);
  }

  public Relationship requestFriendship(User requester, User requested) {
    // TODO figure out what to do if there is a pending friend request the other way - should automatically create the friendship
    return relationshipRepository.findFirstByRequesterAndRequested(requester, requested)
        .map((relationship) -> {
          if (!(relationship.isFriendRelationship()
              && Objects.equals(relationship.getFriendAccepted(), true))) {
            relationship.setFriendRelationship(true);
            relationship.setFriendAccepted(null);
            return relationshipRepository.save(relationship);
          } else {
            return relationship;
          }
        })
        .orElseGet(() -> {
          Relationship relationship = new Relationship();
          relationship.setRequester(requester);
          relationship.setRequested(requested);
          relationship.setFriendRelationship(true);
          relationship.setFriendAccepted(null);
          return relationshipRepository.save(relationship);
        });
  }

  public Relationship startFollowing(User follower, User followed) {
    return relationshipRepository.findFirstByRequesterAndRequested(follower, followed)
        .orElseGet(() -> {
          Relationship relationship = new Relationship();
          relationship.setRequester(follower);
          relationship.setRequested(followed);
          relationship.setFriendRelationship(false);
          relationship.setFriendAccepted(null);
          return relationshipRepository.save(relationship);
        });
  }

  public Relationship save(Relationship relationship) {
    return relationshipRepository.save(relationship);
  }

  public boolean setFriendshipAccepted(Relationship friendship, boolean accepted) {
    friendship.setFriendAccepted(accepted);
    relationshipRepository.save(friendship);
    return accepted;
  }

}
