package edu.cnm.deepdive.tunefull.model.entity;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user_profile")
public class User {

  @Id
  @GeneratedValue
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(name = "favorite_genre", nullable = false)
  private Genre genre;

  @Column(nullable = false, updatable = false, unique = true)
  private String oauth;

  // How to use mappedBy with user1 and user2?
  @NonNull
  @OneToMany(mappedBy = "user1", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("user2 ASC")
  private final List<Relationship> relationships = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("dateTimePosted DESC")
  private final List<Clip> clips = new LinkedList<>();

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Genre getGenre() {
    return genre;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public String getOauth() {
    return oauth;
  }

  public void setOauth(String oauth) {
    this.oauth = oauth;
  }

  @NonNull
  public List<Relationship> getRelationships() {
    return relationships;
  }

  @NonNull
  public List<Clip> getClips() {
    return clips;
  }

  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN
  }
}
