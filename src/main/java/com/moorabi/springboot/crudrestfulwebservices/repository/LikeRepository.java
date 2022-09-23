package com.moorabi.springboot.crudrestfulwebservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.springboot.crudrestfulwebservices.model.Like;
import com.moorabi.springboot.crudrestfulwebservices.model.Reel;
import com.moorabi.springboot.crudrestfulwebservices.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
	
	@Query(value ="select l from Like l where l.reel=?1")
	List<Like> findAllForReel(Reel reel);
	
	@Query(value = "select l from Like l where l.reel=?1 and l.user=?2")
	Like findLike(Reel reel,User user);
	
	@Query(value = "delete from Like l where l.reel=?1 and l.user=?2")
	boolean deleteLike(Reel reel,User user);
}
