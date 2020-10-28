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
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    indexes = {@Index(columnList = "dateTimePosted")}
)
public class Clip {

  // Added NonNull annotation to multiple fields; regenerated getters/setters to reflect changes.

  // Specified GenerationType
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "clip_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String songTitle;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String artist;

  @Column(nullable = true, updatable = false)
  private String album;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String trackKey;

  @Column(updatable = false)
  private int beginTimestamp;

  @Column(updatable = false)
  private int endTimestamp;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date dateTimePosted;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  public Long getId() {
    return id;
  }

  @NonNull
  public String getSongTitle() {
    return songTitle;
  }

  public void setSongTitle(@NonNull String songTitle) {
    this.songTitle = songTitle;
  }

  @NonNull
  public String getArtist() {
    return artist;
  }

  public void setArtist(@NonNull String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  @NonNull
  public String getTrackKey() {
    return trackKey;
  }

  public void setTrackKey(@NonNull String trackKey) {
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

  @NonNull
  public Date getDateTimePosted() {
    return dateTimePosted;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }
}
