package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.ClipRepository;
import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.dao.UserRepository;
import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.Relationship;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClipService {

  private final ClipRepository clipRepository;

  @Autowired
  public ClipService(ClipRepository clipRepository, UserRepository userRepository,
      RelationshipRepository relationshipRepository) {
    this.clipRepository = clipRepository;
  }

  public Optional<Clip> get(long id) {
    return clipRepository.findById(id);
  }

  public List<Clip> getAllFiltered(User user, int limit, int offset, Source source) {
    switch (source) {
      case ALL:
        return clipRepository.getAllByLimitAndOffset(limit, offset);
      case ME:
        return clipRepository.getAllByUserAndLimitAndOffset(user.getId(), limit, offset);
      case FRIENDS:
        return clipRepository.getAllByUserIsInOrderByDateTimePostedDesc(
            user.getFriendships()
                .stream()
                .map((relationship) -> relationship.other(user))
                .collect(Collectors.toList()),
            limit,
            offset);
      case FOLLOWING:
        return clipRepository.getAllByUserIsInOrderByDateTimePostedDesc(
            user.getFollowing()
                .stream()
                .map((relationship) -> relationship.other(user))
                .collect(Collectors.toList()),
            limit,
            offset);
      case RELATIONSHIPS:
        return clipRepository.getAllByUserIsInOrderByDateTimePostedDesc(
            Stream.concat(user.getFriendships().stream(), user.getFollowing().stream())
                .map((relationship) -> relationship.other(user))
                .collect(Collectors.toList()),
            limit,
            offset
        );
      default:
        return null;
    }
  }

  public List<Clip> getAllForDiscovery(int limit, int offset) {
    return clipRepository.getAllByLimitAndOffset(limit, offset);
  }

  public Clip post(Clip clip) {
    return clipRepository.save(clip);
  }

  public void delete(Clip clip) {
    clipRepository.delete(clip);
  }

  public enum Source {
    ME, FRIENDS, FOLLOWING, RELATIONSHIPS, ALL
  }

}
