package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

  private final RelationshipRepository relationshipRepository;

  public RelationshipService(RelationshipRepository relationshipRepository) {
    this.relationshipRepository = relationshipRepository;
  }

  public Relationship get(long id) {
    return relationshipRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
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

  public Relationship post(Relationship relationship) {
    return relationshipRepository.save(relationship);
  }

  public Relationship update(Relationship relationship) {
    return relationshipRepository.save(relationship);
    // TODO get help with this one because I don't think this is the right way
  }

}
