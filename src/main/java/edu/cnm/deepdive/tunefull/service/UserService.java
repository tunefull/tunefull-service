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
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository userRepository;
  private final RelationshipRepository relationshipRepository;

  @Autowired
  public UserService(UserRepository userRepository,
      RelationshipRepository relationshipRepository) {
    this.userRepository = userRepository;
    this.relationshipRepository = relationshipRepository;
  }

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

  public Optional<User> get(long id) {
    return userRepository.findById(id);
  }

  public List<User> getAll() {
    return userRepository.getAllByOrderByUsernameAsc();
  }

  public Genre updateGenre(User user, Genre genre) {
    user.setGenre(genre);
    return userRepository.save(user).getGenre();
  }

  public void delete(User user) {
    userRepository.delete(user);
  }

}
