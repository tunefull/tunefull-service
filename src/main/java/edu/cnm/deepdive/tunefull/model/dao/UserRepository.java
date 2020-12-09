package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * {@code UserRepository} holds data for {@link User}. Aside from the CRUD methods provided by the
 * extension of JpaRepository, {@code findFirstByOauth} and {@code getAllOrderByUsername} provide
 * for certain queries of the database.
 * </p>
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Gets a single user based on the oauth key.
   *
   * @param oauth String
   * @return Optional&lt;User&gt;
   */
  Optional<User> findFirstByOauth(String oauth);

  /**
   * Gets all users in the system, ordered by username alphabetically.
   *
   * @return List&lt;User&gt;
   */
  List<User> getAllByOrderByUsernameAsc();

}
