package edu.cnm.deepdive.tunefull.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
  @JoinColumn(name = "user_1_id", nullable = false, updatable = false)
  private User user1;

  @ManyToOne
  @JoinColumn(name = "user_2_id", nullable = false, updatable = false)
  private User user2;

  @Column(updatable = true)
  private boolean friendRelationship;

  @Column(nullable = true, updatable = true)
  private Boolean friendAccepted;

  public Long getId() {
    return id;
  }

  public User getUser1() {
    return user1;
  }

  public void setUser1(User user1) {
    this.user1 = user1;
  }

  public User getUser2() {
    return user2;
  }

  public void setUser2(User user2) {
    this.user2 = user2;
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
