package com.moorabi.springboot.crudrestfulwebservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.springboot.crudrestfulwebservices.model.Reel;
import com.moorabi.springboot.crudrestfulwebservices.model.User;

@Repository
public interface ReelRepository extends JpaRepository<Reel,Long> {
	
	
	@Query("select r from Reel r where r.user=?1")	
	List<Reel> findByUser(User user);
	
	@Query("select r from Reel r where r.user=?1 and r.id=?2")		
	Reel findReelByUser(User user, long id);
	
	@Query("select r from Reel r where r.country=?1 and r.city=?2")		
	List<Reel> findReelsByCity(String country, String city);
}
