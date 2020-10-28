package edu.cnm.deepdive.tunefull.model.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(nullable = false, unique = true)
  private String username;

  @NonNull
  @Column(nullable = false)
  private String email;

  @NonNull
  @Column(name = "favorite_genre", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Genre genre;

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  private String oauth;

  @NonNull
  @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("requested ASC")
  private final List<Relationship> relationshipsInitiated = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "requested", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("requester ASC")
  private final List<Relationship> relationshipsReceived = new LinkedList<>();

  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("dateTimePosted DESC")
  private final List<Clip> clips = new LinkedList<>();

  public Long getId() {
    return id;
  }

  @NonNull
  public String getUsername() {
    return username;
  }

  public void setUsername(@NonNull String username) {
    this.username = username;
  }

  @NonNull
  public String getEmail() {
    return email;
  }

  public void setEmail(@NonNull String email) {
    this.email = email;
  }

  @NonNull
  public Genre getGenre() {
    return genre;
  }

  public void setGenre(@NonNull Genre genre) {
    this.genre = genre;
  }

  @NonNull
  public String getOauth() {
    return oauth;
  }

  public void setOauth(@NonNull String oauth) {
    this.oauth = oauth;
  }

  @NonNull
  public List<Relationship> getRelationshipsInitiated() {
    return relationshipsInitiated;
  }

  @NonNull
  public List<Relationship> getRelationshipsReceived() {
    return relationshipsReceived;
  }

  @NonNull
  public List<Clip> getClips() {
    return clips;
  }

  // Returns an unsorted list (sort will be needed later to get alphabetical order)
  @NonNull
  public List<Relationship> getFriendships() {
    return Stream.concat(relationshipsInitiated.stream(), relationshipsReceived.stream())
        .filter(Relationship::getFriendAccepted)
        .collect(Collectors.toList());
  }

  @NonNull
  public List<Relationship> getFollowing() {
    return relationshipsInitiated.stream()
        .filter(relationship -> (!relationship.isFriendRelationship()
            || (relationship.getFriendAccepted() != null || !relationship.getFriendAccepted())))
        .collect(Collectors.toList());
  }

  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN, FILM
  }
}
