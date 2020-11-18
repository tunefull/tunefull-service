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


/**
 * Clip entity holds data model for clips of music on Tunefull application
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
    indexes = {@Index(columnList = "dateTimePosted")}
)
public class Clip {

  /**
   * Holds Id value
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "clip_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds songTitle value
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String songTitle;

  /**
   * Holds artist value
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String artist;

  /**
   * Holds album value
   */
  @Column(updatable = false)
  private String album;

  /**
   * Holds trackKey value
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String trackKey;

  /**
   * Holds beginTimestamp value
   */
  @Column(updatable = false)
  private int beginTimestamp;

  /**
   * Holds endTimestamp value
   */
  @Column(updatable = false)
  private int endTimestamp;

  /**
   * Holds dateTimePosted value
   */
  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date dateTimePosted;

  /**
   * Holds user value
   */
  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  /**
   *
   * @return return id
   */
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
