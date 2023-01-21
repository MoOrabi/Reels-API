package com.moorabi.reelsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.model.Reel;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

	@Query("select c from Comment c where c.reel=?1")
	List<Comment> findAllForReel(Reel reel);
}
