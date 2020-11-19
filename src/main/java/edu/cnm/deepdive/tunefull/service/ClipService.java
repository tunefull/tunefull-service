package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.ClipRepository;
import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.dao.UserRepository;
import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * {@code ClipService} provides a layer between {@link ClipRepository} and {@link
 * edu.cnm.deepdive.tunefull.controller.ClipController} for business logic.
 * </p>
 *
 * This class also contains a nested enum, which enumerates types of lists of clips that can be
 * returned by methods in this class.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@Service
public class ClipService {

  private final ClipRepository clipRepository;

  /**
   * Autowired constructor for {@code ClipService}.
   *
   * @param clipRepository         - ClipRepository
   * @param userRepository         - UserRepository
   * @param relationshipRepository - RelationshipRepository
   */
  @Autowired
  public ClipService(ClipRepository clipRepository, UserRepository userRepository,
      RelationshipRepository relationshipRepository) {
    this.clipRepository = clipRepository;
  }

  /**
   * Returns a single clip by id.
   *
   * @param id - long type
   * @return Optional&ltClip&gt
   */
  public Optional<Clip> get(long id) {
    return clipRepository.findById(id);
  }

  /**
   * Returns a list of clips, limited and controlled by the parameters. The source parameter
   * controls whether to return all clips, clips from the current user, or clips from the user's
   * friends, follows, or relationships in general.
   *
   * @param user   - User
   * @param limit  -  int
   * @param offset - int
   * @param source - Source
   * @return List&ltClips&gt
   */
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
            offset);
      default:
        return null;
    }
  }

  /**
   * Returns all clips, limited by parameters. Does not require the current user to be
   * authenticated, and so is used in Discovery mode for users who are not logged in.
   *
   * @param limit  - int
   * @param offset - int
   * @return List&ltClips&gt
   */
  public List<Clip> getAllForDiscovery(int limit, int offset) {
    return clipRepository.getAllByLimitAndOffset(limit, offset);
  }

  /**
   * Posts a clip.
   *
   * @param clip - Clip
   * @return the clip that was posted
   */
  public Clip post(Clip clip) {
    return clipRepository.save(clip);
  }

  /**
   * Deletes a clip.
   *
   * @param clip - Clip
   */
  public void delete(Clip clip) {
    clipRepository.delete(clip);
  }

  /**
   * The Source enum enumerates the different types of lists of clips available for access.
   */
  public enum Source {
    ME, FRIENDS, FOLLOWING, RELATIONSHIPS, ALL
  }

}
