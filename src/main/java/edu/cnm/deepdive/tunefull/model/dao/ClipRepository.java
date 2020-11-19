package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * {@code ClipRepository} holds data for {@link Clip}. Aside from the CRUD methods provided by the
 * extension of JpaRepository, {@code getAllByLimitAndOffset}, {@code
 * getAllByUserAndLimitAndOffset}, and {@code getAllByUserIsInOrderByDateTimePostedDesc} provide for
 * certain queries of the database.
 * </p>
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("SqlResolve")
public interface ClipRepository extends JpaRepository<Clip, Long> {

  /**
   * Gets all clips, limited by parameters, for use in Discovery mode.
   *
   * @param limit  -  int
   * @param offset - int
   * @return List&ltClip&gt
   */
  @Query(value = "SELECT * FROM Clip ORDER BY date_time_posted DESC "
      + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
  List<Clip> getAllByLimitAndOffset(int limit, int offset);

  /**
   * Gets all clips for a particular user, limited by parameters.
   *
   * @param userId - long
   * @param limit  -  int
   * @param offset - int
   * @return List&ltClip&gt
   */
  @Query(value = "SELECT * FROM Clip WHERE user_id = :userId ORDER BY date_time_posted DESC "
      + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
  List<Clip> getAllByUserAndLimitAndOffset(long userId, int limit, int offset);


  /**
   * Gets all clips for users in a collection, limited by parameters.
   *
   * @param users - Collection&ltUser&gt
   * @return List&ltClip&gt
   */
  @Query(value = "SELECT * FROM Clip WHERE user_id IN :users ORDER BY date_time_posted DESC "
      + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY", nativeQuery = true)
  List<Clip> getAllByUserIsInOrderByDateTimePostedDesc(Collection<User> users, int limit,
      int offset);

}
