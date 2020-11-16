package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * <p>
 *   Clip repository holds data for Clips with the following methods; findClipByID,
 *   getClipsByUserIsNotNullOrderByDateTimePostedDesc, getClipsByUserIdOrderByDateTimePostedDesc
 *   getClipsByUserIsInOrderByDateTimePostedDesc
 * </p>
 *
 * @Author Roderick Frechette
 * @Author Laura Steiner
 * @Author Robert Dominguez
 *
 * */
@SuppressWarnings("SqlResolve")
public interface ClipRepository extends JpaRepository<Clip, Long> {

 /**
  * Gets all clips ordered by most recent-less recent
  * */
  List<Clip> getAllByOrderByDateTimePostedDesc();

  // this gets all clips for Discovery
  // DERBY-specific
  @Query(value = "SELECT * FROM Clip ORDER BY date_time_posted DESC "
      + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
  List<Clip> getAllByLimitAndOffset(int limit, int offset);
/**
 * Will this also be the one that we use when getting clips for users that
  are friends or follows?
  Gets all clips for a particular user, ordered by most recent-less recent
 */
  List<Clip> getAllByUserAndLimitAndOffset(User user);

  @Query(value = "SELECT * FROM Clip WHERE user_id = :userId ORDER BY date_time_posted DESC "
      + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
  List<Clip> getAllByUserAndLimitAndOffset(long userId, int limit, int offset);


  /**
 *  or maybe that would be something like this:
 * */
  List<Clip> getAllByUserIsInOrderByDateTimePostedDesc(Collection<User> users);

}
