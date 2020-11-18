package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p> RelationshipRepository holds data for relationship, with the following methods;
 * getRelationshipsByRequesterAndFriendRelationshipTrue, getRelationshipsByRequestedAndFriendRelationshipTrue,
 * getRelationshipsByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue,
 * getRelationshipsByRequesterAndFriendRelationshipFalse, getRelationshipsByRequestedAndFriendAcceptedNull.
 * </p>
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 */

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

  /**
   * The following two would have to be combined, as in line 20?? Gets all friendships for a
   * particular requester, in no particular order
   *
   * @param requester- User type
   * @return list of Relationship
   */
  List<Relationship> getAllByRequesterAndFriendRelationshipTrue(User requester);

  /**
   * Gets all accepted friendships for a particular requested, in no particular order
   *
   * @param requested- User type
   * @return list of Relationship
   */
  List<Relationship> getAllByRequestedAndFriendRelationshipTrue(User requested);

  /**
   * Gets all accepted friendships for a particular user - requester and requested should be the
   * same id in this case, just passed in twice
   *
   * @param requester- User type
   * @param requested- User type
   * @return list of relationship
   */
  List<Relationship>
  getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(
      User requester, User requested);

  /**
   * Gets all follows for a particular user/requester
   *
   * @param requester- User type
   * @return list of relationship
   */
  List<Relationship> getAllByRequesterAndFriendRelationshipFalse(User requester);

  /**
   * Gets all unaccepted friendships for a particular requested (i.e., requests that that user
   * hasn't responded to yet).
   *
   * @param requested- User type
   * @return list of Relationship
   */
  List<Relationship> getAllByRequestedAndFriendAcceptedNull(User requested);

  Optional<Relationship> findFirstByRequesterAndRequested(User requester, User requested);

}
