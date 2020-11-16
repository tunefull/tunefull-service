package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * <p>
 *   Clip repository holds data for Clips with the following methods; findClipByID,
 *   getClipsByUserIsNotNullOrderByDateTimePostedDesc, getClipsByUserIdOrderByDateTimePostedDesc
 *   getClipsByUserIsInOrderByDateTimePostedDesc
 * </p>
 *
 * @Author Roderick Frechette
 * @Author Laura Steiner
 * @Author Robert Dominguez
 *
 * */
public interface ClipRepository extends JpaRepository<Clip, Long> {

 /**
  * Gets all clips ordered by most recent-less recent
  * */
  List<Clip> getAllByOrderByDateTimePostedDesc();

/**
 * Will this also be the one that we use when getting clips for users that
  are friends or follows?
  Gets all clips for a particular user, ordered by most recent-less recent
 */
  List<Clip> getAllByUserOrderByDateTimePostedDesc(User user);

/**
 *  or maybe that would be something like this:
 * */
  List<Clip> getAllByUserIsInOrderByDateTimePostedDesc(Collection<User> users);

}
