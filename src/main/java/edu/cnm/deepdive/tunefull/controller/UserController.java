package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.model.entity.User.Genre;
import edu.cnm.deepdive.tunefull.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code UserController} provides endpoints which allow the client to access data relating to
 * {@link User}.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {

  private final UserService userService;

  /**
   * Autowired constructor for {@code UserController}.
   *
   * @param userService UserService
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Gets the current user.
   *
   * @param auth Authentication
   * @return the current user
   */
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  /**
   * Gets a selected user.
   *
   * @param userId long
   * @param auth Authentication
   * @return Optional&ltUser&gt
   */
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<User> get(@PathVariable long userId, Authentication auth) {
    // TODO investigate returning limited information if this isn't the current user
    return userService.get(userId);
  }

  /**
   * Gets all users, limited by parameters.
   *
   * @param auth Authentication
   * @param limit int
   * @param offset int
   * @return List&ltUser&gt
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAll(Authentication auth,
      @RequestParam(required = false, defaultValue = "40") int limit,
      @RequestParam(required = false, defaultValue = "0") int offset) {
    return userService.getAll();
  }

  /**
   * Updates the current user's favorite genre.
   *
   * @param auth Authentication
   * @param genre Genre enum
   * @return Genre enum
   */
  @PutMapping(value = "/me/genre", consumes = MediaType.TEXT_PLAIN_VALUE,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public Genre updateGenre(Authentication auth, @RequestBody Genre genre) {
    return userService.updateGenre((User) auth.getPrincipal(), genre);
  }

  /**
   * Deletes the current user.
   *
   * @param auth Authentication
   */
  @DeleteMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(Authentication auth) {
    userService.delete((User) auth.getPrincipal());
  }

}
