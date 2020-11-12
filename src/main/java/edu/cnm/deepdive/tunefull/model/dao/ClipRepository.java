package edu.cnm.deepdive.tunefull.model.dao;

import edu.cnm.deepdive.tunefull.model.entity.Clip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClipRepository extends JpaRepository<Clip, Long> {

  //Gets a single clip based on its id
  Optional<Clip> findClipById(Long id);

  //Gets all clips ordered by most recent-less recent
  Optional<List<Clip>> getClipsByUserIsNotNullOrderByDateTimePostedDesc();

  //Gets all clips for a particular user, ordered by most recent-less recent
  Optional<List<Clip>> getClipsByUserIdOrderByDateTimePostedDesc(Long id);

}
