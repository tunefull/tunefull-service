package edu.cnm.deepdive.tunefull.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Relationship {

  @Id
  @GeneratedValue
  @Column(name = "relationship_id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "requester_id", nullable = false, updatable = false)
  private User requester;

  @ManyToOne
  @JoinColumn(name = "requested_id", nullable = false, updatable = false)
  private User requested;

  @Column
  private boolean friendRelationship;

  @Column
  private Boolean friendAccepted;

  public Long getId() {
    return id;
  }

  public User getRequester() {
    return requester;
  }

  public void setRequester(User requester) {
    this.requester = requester;
  }

  public User getRequested() {
    return requested;
  }

  public void setRequested(User requested) {
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
