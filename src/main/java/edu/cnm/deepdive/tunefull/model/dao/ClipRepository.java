package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import edu.cnm.deepdive.tunefull.model.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
 * Gets a single clip based on its id
 * */
  Optional<Clip> findClipById(Long id);

 /**
  * Gets all clips ordered by most recent-less recent
  * */
  Optional<List<Clip>> getClipsByUserIsNotNullOrderByDateTimePostedDesc();

/**
 * Will this also be the one that we use when getting clips for users that
  are friends or follows?
  Gets all clips for a particular user, ordered by most recent-less recent
 */
  Optional<List<Clip>> getClipsByUserIdOrderByDateTimePostedDesc(Long id);

/**
 *  or maybe that would be something like this:
 * */
  Optional<List<Clip>> getClipsByUserIsInOrderByDateTimePostedDesc(Collection<User> users);

}
