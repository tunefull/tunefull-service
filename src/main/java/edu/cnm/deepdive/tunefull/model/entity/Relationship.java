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
 * Relationship entity holds data model for types of relationships on Tunefull application
 *
 * @author Robert Dominguez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
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
   * Holds value for id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "relationship_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds requester value
   */
  @NonNull
  @ManyToOne
  @JoinColumn(name = "requester_id", nullable = false, updatable = false)
  private User requester;

  /**
   * Holds requested value
   */
  @NonNull
  @ManyToOne
  @JoinColumn(name = "requested_id", nullable = false, updatable = false)
  private User requested;

  /**
   * Holds friendRelationship value
   */
  private boolean friendRelationship;

  /**
   * holds friendAccepted value
   */
  private Boolean friendAccepted;

  public Long getId() {
    return id;
  }

  @NonNull
  public User getRequester() {
    return requester;
  }

  public void setRequester(@NonNull User requester) {
    this.requester = requester;
  }

  @NonNull
  public User getRequested() {
    return requested;
  }

  public void setRequested(@NonNull User requested) {
    this.requested = requested;
  }

  public boolean isFriendRelationship() {
    return friendRelationship;
  }

  public void setFriendRelationship(boolean friendRelationship) {
    this.friendRelationship = friendRelationship;
  }

  public Boolean getFriendAccepted() {
    return friendAccepted;
  }

  public void setFriendAccepted(Boolean friendAccepted) {
    this.friendAccepted = friendAccepted;
  }

  public User other(User self) {
    return (self.getId().equals(requester.getId())) ? requested : requester;
  }
}
