package edu.cnm.deepdive.tunefull.service;

import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  //created in repository branch
  //private final UserRepository userRepository;

  @Autowired
  public UserService() {

  }

  public User getOrCreate(String oauthKey, String username /*maybe also some others?*/) {
    /*return repository.getByOauthKey(oauthKey)
    .map((user) -> {
    return repository.save(user);
    })
    .orElseGet(() -> {
    User user = new User();
    user.setOauthKey(oauthKey);
    user.setUsername(username)
    user.set some other stuff???
    return repository.save(user);
    });
    */
    return null;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    Collection<SimpleGrantedAuthority> grants =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//    return new UsernamePasswordAuthenticationToken(
//        getOrCreate()
//    );
    return null;
  }

}
