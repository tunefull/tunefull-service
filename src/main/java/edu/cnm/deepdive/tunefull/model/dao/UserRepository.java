package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *   User repository holds data for User with the following two methods,
 *   getUserByOauth and getUsersByOauthExistsOrderByUserName. This extends Jpa repository.
 * </p>
 *
 * @Author Roderick Frechette
 * @Author Laura Steiner
 * @Author Robert Dominguez
 * */
public interface UserRepository extends JpaRepository<User, Long> {

/**
 *  Gets a single user based on the oauth key
 * */
  Optional<User> getUserByOauth(String oauth);

/**
 *  Gets all users in the system, ordered by username
 *
 * */
  Optional<List<User>> getUsersByOauthExistsOrderByUsername();

}
