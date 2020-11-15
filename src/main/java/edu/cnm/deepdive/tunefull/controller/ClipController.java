package edu.cnm.deepdive.tunefull.controller;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.service.ClipService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  // /clips/me: returns all of the current user's clips
  //again, need to do query params: number of clips to return and index
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> myClips(Authentication auth) {
    return null; //(List<Clip>) ??
  }

  // /clips: returns all of the most recent clips for use in Discovery
  // again needs query params: number and index
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getAll(Authentication auth) {
    return null;
  }

  // /clips/clipId: returns a selected clip
  @GetMapping(value = "/clips/{clipId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip get(Authentication auth) {
    return null; //(Clip) getClip???
  }

  // /clips/friends-follows: gets all clips for users the current user is friends
  // with or following
  //again needs query params: number and index
  @GetMapping(value = "friends-follows", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Clip> getFriendsFollows(Authentication auth) {
    return null;
  }

  // /clips: posts a clip for the current user
  @PostMapping(value = "/clips", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Clip postClip(Authentication auth) {
    return null;
  }

}
