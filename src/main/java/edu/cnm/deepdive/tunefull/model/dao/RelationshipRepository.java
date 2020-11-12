package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

  Optional<Relationship> getRelationshipById(Long id);

  // The following two would have to be combined, as in line 20??
  // Gets all friendships for a particular requester, in no particular order
  Optional<List<Relationship>> getRelationshipsByRequesterAndFriendRelationshipTrue(User requester);

  //Gets all accepted friendships for a particular requested, in no particular order
  Optional<List<Relationship>> getRelationshipsByRequestedAndFriendRelationshipTrue(User requested);

  //Gets all accepted friendships for a particular user - requester and requested should be the
  //same id in this case, just passed in twice
  Optional<List<Relationship>>
  getRelationshipsByRequesterAndFriendRelationshipTrueOrRequestedAndFriendRelationshipTrue(
      User requester, User requested);

  //Gets all follows for a particular user/requester
  Optional<List<Relationship>> getRelationshipsByRequesterAndFriendRelationshipFalse(
      User requester);

  //Gets all unaccepted friendships for a particular requested (i.e., requests that that user
  //hasn't responded to yet).
  Optional<List<Relationship>> getRelationshipsByRequestedAndFriendAcceptedNull(User requested);

}
