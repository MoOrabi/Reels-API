package com.moorabi.reelsapi.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.AppUser;


@Repository
public interface UserRepository extends JpaRepository<AppUser,String> {
	
	@Query("SELECT u FROM AppUser u WHERE u.username = ?1")
	AppUser findUserByUsername(String username);
	
	Optional<AppUser> findByEmail(String email);
	
	Optional<AppUser> findById(String id);
	
	@Query(value =  "select u from AppUser u "
			+ "where u.id in "
			+ "(select t.appUser from Token t  "
			+ "where (t.expired = true or t.revoked = true))")
	List<AppUser> findUsersWithInvalidToken();

	@Query(value =  "select u from AppUser u where u.status = true")
	List<AppUser> findOnlineUsers();
}
