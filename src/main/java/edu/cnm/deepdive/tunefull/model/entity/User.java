package edu.cnm.deepdive.tunefull.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * User entity holds the data model for User data and functions in the TuneFull server.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user_profile")
public class User implements Comparable<User> {

  /**
   * Holds an auto-generated id value.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds the user's username.
   */
  @NonNull
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * Holds the user's email.
   */
  @NonNull
  @Column(nullable = false)
  private String email;

  /**
   * Holds the user's favorite genre.
   */
  @Column(name = "favorite_genre")
  @Enumerated(value = EnumType.STRING)
  private Genre genre;

  /**
   * Holds the oauth value.
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
  @JsonIgnore //for now
  private final List<Relationship> relationshipsInitiated = new LinkedList<>();

  /**
   * Holds relationshipsReceived Value
   */
  @NonNull
  @OneToMany(mappedBy = "requested", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("requester ASC")
  @JsonIgnore //for now
  private final List<Relationship> relationshipsReceived = new LinkedList<>();

  /**
   * Holds clips value
   */
  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("dateTimePosted DESC")
  @JsonIgnore //for now
  private final List<Clip> clips = new LinkedList<>();

  /**
   *
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   *
   * @return username
   */
  @NonNull
  public String getUsername() {
    return username;
  }

  /**
   *
   * @param username- String type
   */
  public void setUsername(@NonNull String username) {
    this.username = username;
  }

  /**
   *
   * @return email
   */
  @NonNull
  public String getEmail() {
    return email;
  }

  /**
   *
   * @param email- String type
   */
  public void setEmail(@NonNull String email) {
    this.email = email;
  }

  /**
   *
   * @return genre
   */
  public Genre getGenre() {
    return genre;
  }

  /**
   *
   * @param genre- enum type
   */
  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  /**
   *
   * @return oauth
   */
  @NonNull
  public String getOauth() {
    return oauth;
  }

  /**
   *
   * @param oauth- String type
   */
  public void setOauth(@NonNull String oauth) {
    this.oauth = oauth;
  }

  /**
   *
   * @return relationshipsInitiated
   */
  @NonNull
  public List<Relationship> getRelationshipsInitiated() {
    return relationshipsInitiated;
  }

  /**
   *
   * @return relationshipsReceived
   */
  @NonNull
  public List<Relationship> getRelationshipsReceived() {
    return relationshipsReceived;
  }

  /**
   *
   * @return clips
   */
  @NonNull
  public List<Clip> getClips() {
    return clips;
  }

  /**
   *
   * @return relationshipsInitiated, relationshipsReceived
   */
  @NonNull
  @JsonIgnore //for now
  public List<Relationship> getFriendships() {
    return Stream.concat(relationshipsInitiated.stream(), relationshipsReceived.stream())
        .filter(Relationship::isFriendRelationship)
        .filter(Relationship::getFriendAccepted)
        .sorted(
            Comparator.comparing((Relationship relationship) -> relationship.other(this).username))
        .collect(Collectors.toList());
  }

  /**
   *
   * @return relationshipsInitiated
   */
  @NonNull
  @JsonIgnore //for now
  public List<Relationship> getFollowing() {
    return relationshipsInitiated.stream()
        .filter(Predicate.not(Relationship::isFriendRelationship)
            .or((relationship) -> relationship.getFriendAccepted() == null)
            .or((relationship) -> relationship.getFriendAccepted().equals(false)))
        .sorted(Comparator.comparing((relationship) -> relationship.getRequested().username))
        .collect(Collectors.toList());
  }

  /**
   *
   * @param other- User type
   * @return username
   */
  @Override
  public int compareTo(User other) {
    return username.compareToIgnoreCase(other.username);
  }

  /**
   * Holds different music genres to select from
   */
  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN, FILM
  }
}
