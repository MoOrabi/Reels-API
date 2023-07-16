package com.moorabi.reelsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.Like;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.AppUser;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
	
	@Query(value ="select l from Like l where l.reel=?1")
	List<Like> findAllForReel(Reel reel);
	
	@Query(value = "select l from Like l where l.reel=?1 and l.appUser=?2")
	Like findLike(Reel reel,AppUser appUser);
	
	@Modifying
	@Query(value = "delete from Like l where l.reel=?1 and l.appUser=?2")
	int deleteLike(Reel reel,AppUser appUser);
}
