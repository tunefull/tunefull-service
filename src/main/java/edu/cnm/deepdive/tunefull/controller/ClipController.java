package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.dao.ClipRepository;
import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import edu.cnm.deepdive.tunefull.service.ClipService;
import edu.cnm.deepdive.tunefull.service.ClipService.Source;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clips")
@ExposesResourceFor(Clip.class)
public class ClipController {

  private final ClipService clipService;

  @Autowired
  public ClipController(ClipService clipService) {
    this.clipService = clipService;
  }

  // /clips: returns all of the most recent clips OR only clips by friends OR only clips by followers
  // OR only clips by relationships with the user OR only clips by the user
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getAll(Authentication auth,
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "ALL") Source source) {
    return clipService.getAllFiltered((User) auth.getPrincipal(), limit, offset, source);
  }

  // returns clips for discovery (no auth needed)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getAll(
      @RequestParam(required = false, defaultValue = "10") int limit,
      @RequestParam(required = false, defaultValue = "0") int offset) {
    return clipService.getAllForDiscovery(limit, offset);
  }

  // /clips/clipId: returns a selected clip
  @GetMapping(value = "/{clipId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip get(Authentication auth, @PathVariable long clipId) {
    return clipService.get(clipId)
        .orElseThrow(NoSuchElementException::new);
  }

  // /clips: posts a clip for the current user
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip post(Authentication auth, @RequestBody Clip clip) {
    return clipService.post(clip);
  }

  // deletes a clip only if it has been posted by the current user
  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void delete(Authentication auth, @RequestBody Clip clip) {
    if (clip.getUser() == auth.getPrincipal()) {
      clipService.delete(clip);
    }
  }
}
