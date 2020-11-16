package edu.cnm.deepdive.tunefull.model.entity;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
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
import org.springframework.util.comparator.Comparators;

/**
 * User entity holds data model for User data and functions on Tunefull application
 *
 * @Author Roderick Frechette
 * @Author Laura Steiner
 * @Author Robert Dominguez
 *
 * @Version 1.0
 * @Since 1.0
 */

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user_profile")
public class User implements Comparable<User> {

  /**
   * Holds id value
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds username value
   */
  @NonNull
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * Holds email value
   */
  @NonNull
  @Column(nullable = false)
  private String email;

  /**
   * Holds genre value
   */
  @Column(name = "favorite_genre")
  @Enumerated(value = EnumType.STRING)
  private Genre genre;

  /**
   * Holds oauth value
   */
  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  private String oauth;

  /**
   * Holds relationshipsInitiated value
   */
  @NonNull
  @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("requested ASC")
  private final List<Relationship> relationshipsInitiated = new LinkedList<>();

  /**
   * Holds relationshipsReceived Value
   */
  @NonNull
  @OneToMany(mappedBy = "requested", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("requester ASC")
  private final List<Relationship> relationshipsReceived = new LinkedList<>();

  /**
   * Holds clips value
   */
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

  @NonNull
  public List<Relationship> getFriendships() {
    return Stream.concat(relationshipsInitiated.stream(), relationshipsReceived.stream())
        .filter(Relationship::isFriendRelationship)
        .filter(Relationship::getFriendAccepted)
        .sorted(Comparator.comparing((Relationship relationship) -> relationship.other(this).username))
        .collect(Collectors.toList());
  }

  @NonNull
  public List<Relationship> getFollowing() {
    return relationshipsInitiated.stream()
        .filter(Predicate.not(Relationship::isFriendRelationship)
            .or((relationship) -> relationship.getFriendAccepted() == null)
            .or((relationship) -> relationship.getFriendAccepted().equals(false)))
        .sorted(Comparator.comparing((relationship) -> relationship.getRequested().username))
        .collect(Collectors.toList());
  }

  @Override
  public int compareTo(User other) {
    return username.compareToIgnoreCase(other.username);
  }

  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN, FILM
  }
}
