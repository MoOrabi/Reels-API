package com.moorabi.reelsapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	User findUserByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}
