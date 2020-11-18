package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * User repository holds data for User with the following two methods, getUserByOauth and
 * getUsersByOauthExistsOrderByUserName. This extends Jpa repository.
 * </p>
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Gets a single user based on the oauth key
   * @param oauth- String value
   * @return
   */
  Optional<User> findFirstByOauth(String oauth);

  /**
   * Gets all users in the system, ordered by username
   */
  List<User> getAllByOrderByUsername();

}
