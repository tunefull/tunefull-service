package edu.cnm.deepdive.tunefull.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(indexes = {@Index(columnList = "dateTimePosted")})
public class Clip {

  @Id
  @GeneratedValue
  @Column(name = "clip_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, updatable = false)
  private String songTitle;

  @Column(nullable = false, updatable = false)
  private String artist;

  @Column(nullable = true, updatable = false)
  private String album;

  @Column(nullable = false, updatable = false)
  private String trackKey;

  @Column(updatable = false)
  private int beginTimestamp;

  @Column(updatable = false)
  private int endTimestamp;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date dateTimePosted;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  public Long getId() {
    return id;
  }

  public String getSongTitle() {
    return songTitle;
  }

  public void setSongTitle(String songTitle) {
    this.songTitle = songTitle;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getTrackKey() {
    return trackKey;
  }

  public void setTrackKey(String trackKey) {
    this.trackKey = trackKey;
  }

  public int getBeginTimestamp() {
    return beginTimestamp;
  }

  public void setBeginTimestamp(int beginTimestamp) {
    this.beginTimestamp = beginTimestamp;
  }

  public int getEndTimestamp() {
    return endTimestamp;
  }

  public void setEndTimestamp(int endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  public Date getDateTimePosted() {
    return dateTimePosted;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
