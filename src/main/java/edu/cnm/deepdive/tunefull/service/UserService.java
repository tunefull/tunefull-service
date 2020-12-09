package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.dao.RelationshipRepository;
import edu.cnm.deepdive.tunefull.model.dao.UserRepository;
import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.model.entity.User.Genre;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * {@code UserService} provides a layer between {@link UserRepository} and {@link
 * edu.cnm.deepdive.tunefull.controller.UserController} for business logic.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository userRepository;
  private final RelationshipRepository relationshipRepository;

  /**
   * Autowired constructor for {@code UserService}
   *
   * @param userRepository         UserRepository
   * @param relationshipRepository RelationshipRepository
   */
  @Autowired
  public UserService(UserRepository userRepository, RelationshipRepository relationshipRepository) {
    this.userRepository = userRepository;
    this.relationshipRepository = relationshipRepository;
  }

  /**
   * Gets the user with the oauth key, or creates the user if one does not exist for this oauth.
   *
   * @param oauthKey String
   * @param username String
   * @param email    String
   * @return the User that has been found or created
   */
  public User getOrCreate(String oauthKey, String username, String email) {
    return userRepository.findFirstByOauth(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauth(oauthKey);
          user.setUsername(username);
          user.setEmail(email);
          return userRepository.save(user);
        });
  }

  /**
   * Overrides the {@code convert} method to convert a Json web token into a
   * UsernamePasswordAuthenticationToken
   *
   * @param jwt Jwt
   * @return UsernamePasswordAuthenticationToken
   */
  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaimAsString("name"), jwt.getClaim("email")),
        jwt.getTokenValue(),
        grants
    );
  }

  /**
   * Finds and returns a single User object by id.
   *
   * @param id long
   * @return Optional&lt;User&gt;
   */
  public Optional<User> get(long id) {
    return userRepository.findById(id);
  }

  /**
   * Returns a list of all users, ordered by username in alphabetical order.
   *
   * @return List&lt;User&gt;
   */
  public List<User> getAll() {
    return userRepository.getAllByOrderByUsernameAsc();
  }

  /**
   * Updates the user's favorite genre.
   *
   * @param user  User
   * @param genre Genre enum
   * @return Genre enum
   */
  public Genre updateGenre(User user, Genre genre) {
    user.setGenre(genre);
    return userRepository.save(user).getGenre();
  }

  /**
   * Deletes the current user.
   *
   * @param user User
   */
  public void delete(User user) {
    userRepository.delete(user);
  }

}
