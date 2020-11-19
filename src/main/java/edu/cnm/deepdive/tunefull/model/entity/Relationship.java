package edu.cnm.deepdive.tunefull.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.lang.NonNull;

/**
 * <p>
 * The {@code Relationship} entity holds the data for each user-to-user relationship in the TuneFull
 * server and provides a method to facilitate getting the other user in the relationship.
 * </p>
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"requester_id", "requested_id"})
    }
)
public class Relationship {

  /**
   * Holds an auto-generated id value for identification of the relationship in the TuneFull
   * database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "relationship_id", nullable = false, updatable = false)
  private Long id;

  /**
   * References the {@link User} entity associated with the user who requested the relationship.
   */
  @NonNull
  @ManyToOne
  @JoinColumn(name = "requester_id", nullable = false, updatable = false)
  private User requester;

  /**
   * References the {@link User} entity associated with the user who received a relationship
   * request.
   */
  @NonNull
  @ManyToOne
  @JoinColumn(name = "requested_id", nullable = false, updatable = false)
  private User requested;

  /**
   * If friendRelationship is true, the two users are friends. If it is false, the {@code requester}
   * is simply following the {@code requested}.
   */
  private boolean friendRelationship;

  /**
   * If friendAccepted is null, the user who received the request either has not yet accepted/denied
   * the friend request or the relationship is just a follow relationship. True and false values
   * correspond to accepted and denied requests respectively.
   */
  private Boolean friendAccepted;

  /**
   * Returns the id for the relationship.
   *
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the {@link User} entity associated with the user who requested the relationship.
   *
   * @return
   */
  @NonNull
  public User getRequester() {
    return requester;
  }

  /**
   * Sets the {@link User} entity associated with the user who requested the relationship.
   *
   * @param requester {@link User}
   */
  public void setRequester(@NonNull User requester) {
    this.requester = requester;
  }

  /**
   * Returns the {@link User} entity associated with the user who received the relationship
   * request.
   *
   * @return
   */
  @NonNull
  public User getRequested() {
    return requested;
  }

  /**
   * Sets the {@link User} entity associated with the user who received the relationship request.
   *
   * @param requested {@link User}
   */
  public void setRequested(@NonNull User requested) {
    this.requested = requested;
  }

  /**
   * Returns a boolean indicating whether the relationship is a friendship or simply a follow
   * relationship.
   *
   * @return
   */
  public boolean isFriendRelationship() {
    return friendRelationship;
  }

  /**
   * Sets the boolean indicating whether the relationship is a friendship or simply a follow
   * relationship.
   *
   * @param friendRelationship boolean
   */
  public void setFriendRelationship(boolean friendRelationship) {
    this.friendRelationship = friendRelationship;
  }

  /**
   * Returns the Boolean indicating whether a friend relationship has been accepted, denied, or not
   * yet responded to.
   *
   * @return
   */
  public Boolean getFriendAccepted() {
    return friendAccepted;
  }

  /**
   * Sets the Boolean indicating whether a friend relationship has been accepted, denied, or not yet
   * responded to.
   *
   * @param friendAccepted Boolean
   */
  public void setFriendAccepted(Boolean friendAccepted) {
    this.friendAccepted = friendAccepted;
  }

  /**
   * Returns the user on the other side of the relationship from the current user: the requested if
   * the current user is the requester, and the requester if the current user is the requested.
   *
   * @param self {@link User}
   * @return the user in the relationship that is not the current user
   */
  public User other(User self) {
    return (self.getId().equals(requester.getId())) ? requested : requester;
  }
}
