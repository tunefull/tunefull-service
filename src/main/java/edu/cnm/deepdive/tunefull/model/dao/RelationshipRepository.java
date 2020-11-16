package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p> RelationshipRepository holds data for relationship, with the following methods;
 * getRelationshipsByRequesterAndFriendRelationshipTrue, getRelationshipsByRequestedAndFriendRelationshipTrue,
 * getRelationshipsByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue,
 * getRelationshipsByRequesterAndFriendRelationshipFalse, getRelationshipsByRequestedAndFriendAcceptedNull.
 * </p>
 *
 * @Author Roderick Frechette
 * @Author Laura Steiner
 * @Author Robert Dominguez
 * */

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

/**
 *  The following two would have to be combined, as in line 20??
   Gets all friendships for a particular requester, in no particular order
 */
  List<Relationship> getAllByRequesterAndFriendRelationshipTrue(User requester);

/**
 *  Gets all accepted friendships for a particular requested, in no particular order
 *  */
  List<Relationship> getAllByRequestedAndFriendRelationshipTrue(User requested);

/**
 * Gets all accepted friendships for a particular user - requester and requested should be the
  same id in this case, just passed in twice
 */
  List<Relationship>
  getAllByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(
      User requester, User requested);

/**
 *  Gets all follows for a particular user/requester
 *  */
  List<Relationship> getAllByRequesterAndFriendRelationshipFalse(
      User requester);

/**
*  Gets all unaccepted friendships for a particular requested (i.e., requests that that user
*  hasn't responded to yet).
*/
  List<Relationship> getAllByRequestedAndFriendAcceptedNull(User requested);

}
