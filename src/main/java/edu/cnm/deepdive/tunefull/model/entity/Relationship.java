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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"requester_id", "requested_id"})
    }
) // Inserted uniqueConstraints for the compound unique key ^^^.
public class Relationship {

  // Added NonNull annotation to multiple fields.

  // Specified GenerationType
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "relationship_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "requester_id", nullable = false, updatable = false)
  private User requester;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "requested_id", nullable = false, updatable = false)
  private User requested;

  // Removed unnecessary Column annotation
  private boolean friendRelationship;

  // Removed unnecessary Column annotation
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
}
