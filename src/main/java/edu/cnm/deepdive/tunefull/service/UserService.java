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

@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserRepository repository;
  private final RelationshipRepository relationshipRepository;

  @Autowired
  public UserService(UserRepository repository,
      RelationshipRepository relationshipRepository) {
    this.repository = repository;
    this.relationshipRepository = relationshipRepository;
  }

  public User getOrCreate(String oauthKey, String username, String email) {
    return repository.findFirstByOauth(oauthKey)
        .orElseGet(() -> {
          User user = new User();
          user.setOauth(oauthKey);
          user.setUsername(username);
          user.setEmail(email);
          return repository.save(user);
        });
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    return new UsernamePasswordAuthenticationToken(
        getOrCreate(jwt.getSubject(), jwt.getClaim("name"), jwt.getClaim("email")),
        jwt.getTokenValue(), grants
    );
  }

  public Optional<User> get(long id) {
    return repository.findById(id);
  }

  public List<User> getAll() {
    return repository.getAllByOrderByUsername();
  }

  public Genre updateGenre(User user, Genre genre) {
    user.setGenre(genre);
    return repository.save(user).getGenre();
  }

}
