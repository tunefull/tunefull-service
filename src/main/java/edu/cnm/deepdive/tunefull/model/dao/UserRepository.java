package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  //Gets a single user based on the oauth key
  Optional<User> getUserByOauth(String oauth);

  //Gets all users in the system, ordered by username
  Optional<List<User>> getUsersByOauthExistsOrderByUsername();

}
