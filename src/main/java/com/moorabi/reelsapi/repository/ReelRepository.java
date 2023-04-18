package com.moorabi.reelsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;

@Repository
public interface ReelRepository extends JpaRepository<Reel,Long> {
	
	
	@Query("select r from Reel r, User u where r.user.id=u.id and r.user.id=?1")	
	List<Reel> findByUserId(long id);
	
	@Query("select r from Reel r where r.user=?1 and r.id=?2")		
	Reel findReelByUser(User user, long id);
	
	@Query("select r from Reel r where r.country=?1 and r.city=?2")		
	List<Reel> findReelsByCity(String country, String city);

	@Query("select r from Reel r where r.country=?1")
	List<Reel> findReelsByCountry(String country);
}
