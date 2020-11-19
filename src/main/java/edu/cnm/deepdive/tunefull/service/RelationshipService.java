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

  /**
   *
   * @param relationshipRepository RelationshipRepository type
   */
  public RelationshipService(RelationshipRepository relationshipRepository) {
    this.relationshipRepository = relationshipRepository;
  }

  /**
   * Gets the Relationship by the id if it exists.
   *
   * @param id Long type
   * @return
   */
  public Optional<Relationship> get(long id) {
    return relationshipRepository.findById(id);
  }

  /**
   * Gets the Relationship between two users by the two users.
   *
   * @param user User type
   * @return
   */
  public List<Relationship> getFriendships(User user) {
    return relationshipRepository
        .getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(user, user);
  }

  /**
   * Gets all the Relationships in which the user is following other users
   *
   * @param user User Type
   * @return
   */
  public List<Relationship> getFollows(User user) {
    return relationshipRepository.getAllByRequesterAndFriendRelationshipFalse(user);
  }

  /**
   * Gets all the Relationships in which the user has been sent a friend request and the user
   * has not responded to yet.
   *
   * @param user User type
   * @return
   */
  public List<Relationship> getPending(User user) {
    return relationshipRepository.getAllByRequestedAndFriendAcceptedNull(user);
  }

  /**
   * Creates a relationship between two users in which a friend request has been sent.
   *
   * @param requester User Type
   * @param requested User Type
   * @return
   */
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

  /**
   * Creates a relationship between two users in which one is following the other
   *
   * @param follower User Type
   * @param followed User Type
   * @return
   */
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

  /**
   * Saves the relationship to the repository
   *
   * @param relationship Relationship Type
   * @return
   */
  public Relationship save(Relationship relationship) {
    return relationshipRepository.save(relationship);
  }

  /**
   * Sets the boolean that determines whether a relationship is a friendship or not.
   *
   * @param friendship Relationship type
   * @param accepted boolean
   * @return
   */
  public boolean setFriendshipAccepted(Relationship friendship, boolean accepted) {
    friendship.setFriendAccepted(accepted);
    relationshipRepository.save(friendship);
    return accepted;
  }

}
