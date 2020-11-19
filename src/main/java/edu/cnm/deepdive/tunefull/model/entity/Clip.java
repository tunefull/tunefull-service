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
 * <p>
 * The {@code Clip} entity holds the data for each clip of music shared in the TuneFull server.
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
    indexes = {@Index(columnList = "dateTimePosted")}
)
public class Clip {

  /**
   * Holds an auto-generated id value for identification of the clip in the TuneFull database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "clip_id", nullable = false, updatable = false)
  private Long id;

  /**
   * Holds the title of the song the clip is from.
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String songTitle;

  /**
   * Holds the name of the clip's artist.
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String artist;

  /**
   * Holds the name of the album associated with the clip. This attribute is nullable.
   */
  @Column(updatable = false)
  private String album;

  /**
   * Holds the track key associated with the song on Spotify.
   */
  @NonNull
  @Column(nullable = false, updatable = false)
  private String trackKey;

  /**
   * Holds the timestamp for the beginning of the clip.
   */
  @Column(updatable = false)
  private int beginTimestamp;

  /**
   * Holds the timestamp for the end of the clip.
   */
  @Column(updatable = false)
  private int endTimestamp;

  /**
   * Holds the date and time that the clip was posted.
   */
  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date dateTimePosted;

  /**
   * References the {@link User} who created the clip.
   */
  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  /**
   * Returns the clip's id.
   *
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the title for the song the clip is from .
   *
   * @return
   */
  @NonNull
  public String getSongTitle() {
    return songTitle;
  }

  /**
   * Sets the title for the song the clip is from
   *
   * @param songTitle String
   */
  public void setSongTitle(@NonNull String songTitle) {
    this.songTitle = songTitle;
  }

  /**
   * Returns the name of the clip's artist
   *
   * @return
   */
  @NonNull
  public String getArtist() {
    return artist;
  }

  /**
   * Sets the name of the clip's artist
   *
   * @param artist String
   */
  public void setArtist(@NonNull String artist) {
    this.artist = artist;
  }

  /**
   * Returns the name of the clip's album
   *
   * @return
   */
  public String getAlbum() {
    return album;
  }

  /**
   * Sets the name of the clip's album
   *
   * @param album String
   */
  public void setAlbum(String album) {
    this.album = album;
  }

  /**
   * Returns the track key associated with the song on Spotify.
   *
   * @return
   */
  @NonNull
  public String getTrackKey() {
    return trackKey;
  }

  /**
   * Sets the track key associated with the song on Spotify.
   *
   * @param trackKey String
   */
  public void setTrackKey(@NonNull String trackKey) {
    this.trackKey = trackKey;
  }

  /**
   * Returns the timestamp for the beginning of the clip
   *
   * @return
   */
  public int getBeginTimestamp() {
    return beginTimestamp;
  }

  /**
   * Sets the timestamp for the beginning of the clip
   *
   * @param beginTimestamp int
   */
  public void setBeginTimestamp(int beginTimestamp) {
    this.beginTimestamp = beginTimestamp;
  }

  /**
   * Returns the timestamp for the end of the clip
   *
   * @return
   */
  public int getEndTimestamp() {
    return endTimestamp;
  }

  /**
   * Sets the timestamp for the beginning of the clip
   *
   * @param endTimestamp int
   */
  public void setEndTimestamp(int endTimestamp) {
    this.endTimestamp = endTimestamp;
  }

  /**
   * Returns the date and time that the clip was posted
   *
   * @return
   */
  @NonNull
  public Date getDateTimePosted() {
    return dateTimePosted;
  }

  /**
   * Returns the {@link User} who created the clip
   *
   * @return
   */
  @NonNull
  public User getUser() {
    return user;
  }

  /**
   * Sets the {@link User} who posted the clip
   *
   * @param user {@link User}
   */
  public void setUser(@NonNull User user) {
    this.user = user;
  }
}
