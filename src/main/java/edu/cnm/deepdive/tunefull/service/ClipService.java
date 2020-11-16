package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.ClipRepository;
import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.dao.UserRepository;
import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClipService {

  private final ClipRepository clipRepository;
  private final UserRepository userRepository;
  private final RelationshipRepository relationshipRepository;

  @Autowired
  public ClipService(ClipRepository clipRepository, UserRepository userRepository,
      RelationshipRepository relationshipRepository) {
    this.clipRepository = clipRepository;
    this.userRepository = userRepository;
    this.relationshipRepository = relationshipRepository;
  }

  public List<Clip> getAllFiltered(User user, int limit, int offset, Source source) {
    // TODO methods in ClipRepository that allow us to query clips according to these params
    switch (source) {
      case ALL:
        return clipRepository.getAllByLimitAndOffset(limit, offset);
      case ME:
        return clipRepository.getAllByUserAndLimitAndOffset(user.getId(), limit, offset);
      case FRIENDS:
        // return clipRepository.create this method
      case FOLLOWING:
        //return clipRepository.create this method
      default:
        return null;
    }
  }



  public enum Source {
    ME, FRIENDS, FOLLOWING, ALL
  }

}
