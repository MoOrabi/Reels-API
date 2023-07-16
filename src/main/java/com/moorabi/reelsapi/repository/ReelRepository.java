package com.moorabi.reelsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.AppUser;

@Repository
public interface ReelRepository extends JpaRepository<Reel,Long> {
	
	
	@Query("select r from Reel r where r.appUser.id=?1")	
	List<Reel> findReelsForUserByUserId(String id);
	
	List<Reel> findByCountryAndCity(String country, String city);

	List<Reel> findByCountry(String country);
}
