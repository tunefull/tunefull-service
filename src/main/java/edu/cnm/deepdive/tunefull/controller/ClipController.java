package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.service.ClipService;
import edu.cnm.deepdive.tunefull.service.ClipService.Source;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code ClipController} provides endpoints which allow the client to access data relating to
 * {@link Clip}.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/clips")
@ExposesResourceFor(Clip.class)
public class ClipController {

  private final ClipService clipService;

  /**
   * Autowired constructor for {@code ClipController}.
   *
   * @param clipService ClipService
   */
  @Autowired
  public ClipController(ClipService clipService) {
    this.clipService = clipService;
  }

  /**
   * Returns all of the most recent clips, limited by parameters. The source parameter allows for
   * the client to get all clips, only clips by friends, only clips by followers, only clips by
   * users in a relationship with the user, or only clips by the user.
   *
   * @param auth   Authentication
   * @param limit  int
   * @param offset int
   * @param source Source enum
   * @return List&ltClip&gt
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getAll(Authentication auth,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "ALL") Source source) {
    return clipService.getAllFiltered((User) auth.getPrincipal(), limit, offset, source);
  }

  /**
   * Gets all of the most recent clips, limited by parameters. This overload of the {@code getAll}
   * method allows users who have not logged in to access clips used in Discovery mode.
   *
   * @param limit  int
   * @param offset int
   * @return List&ltClip&gt
   */
  @GetMapping(value = "/discovery", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getAll(
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "0") int offset) {
    return clipService.getAllForDiscovery(limit, offset);
  }

  /**
   * Gets a selected clip by id.
   *
   * @param auth   Authentication
   * @param clipId long
   * @return a selected clip
   */
  @GetMapping(value = "/{clipId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip get(Authentication auth, @PathVariable long clipId) {
    return clipService.get(clipId)
        .orElseThrow(NoSuchElementException::new);
  }

  /**
   * Posts a clip for the current user.
   *
   * @param auth Authentication
   * @param clip Clip
   * @return the clip that was posted
   */
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip post(Authentication auth, @RequestBody Clip clip) {
    clip.setUser((User) auth.getPrincipal());
    return clipService.post(clip);
  }

  /**
   * Deletes a clip only if it has been posted by the current user.
   *
   * @param auth Authentication
   * @param clip Clip
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(Authentication auth, @RequestBody Clip clip) {
    if (clip.getUser() != auth.getPrincipal()) {
      throw new IllegalArgumentException();
    }
    clipService.delete(clip);
  }
}
