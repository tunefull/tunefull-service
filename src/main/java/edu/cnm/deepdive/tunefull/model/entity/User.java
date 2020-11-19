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

/**
 * <p>
 * The {@code User} entity holds the data for each user in the TuneFull server and provides methods
 * to access lists of data.
 * </p>
 * <p>
 * As well as fields of data, and methods to access that data, the {@code User} entity contains the
 * nested enum {@link Genre}, which enumerates a basic list of favorite musical genres for the user
 * to select from.
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "user_profile")
public class User implements Comparable<User> {

  /**
   * Holds an auto-generated id value for identification of the user in the TuneFull database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds the user's username. Each username must be unique.
   */
  @NonNull
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * Holds the user's email address.
   */
  @NonNull
  @Column(nullable = false)
  private String email;

  /**
   * Holds the user's favorite genre, specified as an enumerated type from the {@link Genre} enum.
   */
  @Column(name = "favorite_genre")
  @Enumerated(value = EnumType.STRING)
  private Genre genre;

  /**
   * Holds the OAuth 2.0 key.
   */
  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  private String oauth;

  /**
   * Holds the list of relationships that the user has initiated.
   */
  @NonNull
  @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("requested ASC")
  @JsonIgnore //for now
  private final List<Relationship> relationshipsInitiated = new LinkedList<>();

  /**
   * Holds the list of relationships that the user has received requests for.
   */
  @NonNull
  @OneToMany(mappedBy = "requested", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("requester ASC")
  @JsonIgnore //for now
  private final List<Relationship> relationshipsReceived = new LinkedList<>();

  /**
   * Holds the list of clips posted by the user.
   */
  @NonNull
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("dateTimePosted DESC")
  @JsonIgnore //for now
  private final List<Clip> clips = new LinkedList<>();

  /**
   * Returns the id for the user.
   *
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the user's username.
   *
   * @return
   */
  @NonNull
  public String getUsername() {
    return username;
  }

  /**
   * Sets the user's username.
   *
   * @param username - String
   */
  public void setUsername(@NonNull String username) {
    this.username = username;
  }

  /**
   * Returns the user's email address.
   *
   * @return
   */
  @NonNull
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email address.
   *
   * @param email - String
   */
  public void setEmail(@NonNull String email) {
    this.email = email;
  }

  /**
   * Returns the user's favorite genre as an enumerated type from the {@link Genre} enum.
   *
   * @return
   */
  public Genre getGenre() {
    return genre;
  }

  /**
   * Sets the user's favorite genre.
   *
   * @param genre - an enumerated value from {@link Genre}
   */
  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  /**
   * Returns the user's OAuth 2.0 key.
   *
   * @return
   */
  @NonNull
  public String getOauth() {
    return oauth;
  }

  /**
   * Sets the user's OAuth 2.0 key.
   *
   * @param oauth- String
   */
  public void setOauth(@NonNull String oauth) {
    this.oauth = oauth;
  }

  /**
   * Returns the list of relationships that the user has initiated.
   *
   * @return
   */
  @NonNull
  public List<Relationship> getRelationshipsInitiated() {
    return relationshipsInitiated;
  }

  /**
   * Returns the list of relationships that the user has received requests for.
   *
   * @return
   */
  @NonNull
  public List<Relationship> getRelationshipsReceived() {
    return relationshipsReceived;
  }

  /**
   * Returns the list of clips posted by the user.
   *
   * @return
   */
  @NonNull
  public List<Clip> getClips() {
    return clips;
  }

  /**
   * Returns the friendships in which the user is either the initiator or the recipient of the
   * request.
   *
   * @return
   */
  @NonNull
  @JsonIgnore //for now
  public List<Relationship> getFriendships() {
    return Stream.concat(relationshipsInitiated.stream(), relationshipsReceived.stream())
        .filter(Relationship::isFriendRelationship)
        .filter(Relationship::getFriendAccepted)
        .sorted(
            Comparator.comparing((Relationship relationship)
                -> relationship.other(this).username))
        .collect(Collectors.toList());
  }

  /**
   * Returns the relationships in which the user is following another user
   *
   * @return
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

  @Override
  public int compareTo(User other) {
    return username.compareToIgnoreCase(other.username);
  }

  /**
   * The {@code Genre} enum enumerates different musical genres that the user can select from for
   * their favorite genre.
   */
  public enum Genre {
    CLASSICAL, ROCK_N_ROLL, POP, JAZZ, METAL, HIPHOP, R_AND_B, BLUES, FOLK, OPERA, ELECTRONIC,
    ALTERNATIVE, PUNK, REGGAE, CLASSIC_ROCK, DISCO, SWING, FUNK, COUNTRY, CONJUNTO, LATIN, FILM
  }

}
