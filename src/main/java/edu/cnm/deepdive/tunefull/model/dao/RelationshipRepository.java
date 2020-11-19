package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * {@code RelationshipRepository} holds data for {@link Relationship}. Aside from the CRUD methods
 * provided by the extension of JpaRepository, {@code getAllByRequesterAndFriendRelationshipTrue},
 * {@code getAllByRequestedAndFriendRelationshipTrue}, {@code getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue}
 * {@code getAllByRequesterAndFriendRelationshipFalse}, {@code getAllByRequestedAndFriendAcceptedNull},
 * and {@code findFirstByRequesterAndRequested} provide for certain queries of the database.
 * </p>
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

  /**
   * Gets all friendships for a particular requester.
   *
   * @param requester - User
   * @return List&ltRelationship&gt
   */
  List<Relationship> getAllByRequesterAndFriendRelationshipTrue(User requester);

  /**
   * Gets all friendships for a particular requested.
   *
   * @param requested - User
   * @return List&ltRelationship&gt
   */
  List<Relationship> getAllByRequestedAndFriendRelationshipTrue(User requested);

  /**
   * Gets all friendships for a particular user - the same {@link User} should be used for both
   * requester and requested.
   *
   * @param requester - User
   * @param requested - User
   * @return List&ltRelationship&gt
   */
  List<Relationship>
  getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(User requester,
      User requested);

  /**
   * Gets all follows for a particular user.
   *
   * @param requester - User
   * @return List&ltRelationship&gt
   */
  List<Relationship> getAllByRequesterAndFriendRelationshipFalse(User requester);

  /**
   * Gets all pending friendships for a particular user (i.e., requests that that user hasn't
   * responded to yet).
   *
   * @param requested - User
   * @return List&ltRelationship&gt
   */
  List<Relationship> getAllByRequestedAndFriendAcceptedNull(User requested);

  /**
   * Gets a relationship given its two users.
   *
   * @param requester - User
   * @param requested - User
   * @return Optional&ltRelationship&gt
   */
  Optional<Relationship> findFirstByRequesterAndRequested(User requester, User requested);

}
