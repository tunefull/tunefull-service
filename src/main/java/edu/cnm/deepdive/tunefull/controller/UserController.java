package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.model.entity.User.Genre;
import edu.cnm.deepdive.tunefull.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // /users/me: gets the current user
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  // /users/userId: gets a selected user
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable Long userId, Authentication auth) {
    return null;
//    return userService.getUser(userId)
//        .orElseThrow(NoSuchElementException::new);
  }

  // /users: gets all users, up to a specified number (or default all)
  //how to do query params??
  @GetMapping(params = {}, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAll(Authentication auth) {
    return null; // (List<User>) how to get this list???
  }

  // /users/me/genre: updates the current user's genre
  @PutMapping(value = "/me/genre", consumes = MediaType.TEXT_PLAIN_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public Genre updateGenre(Authentication auth) {
    return null;
  }

}
