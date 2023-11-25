package com.moorabi.reelsapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.moorabi.reelsapi.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{

	@Query(value =  "select t from Token t "
			+ "inner join t.appUser u "
			+ "on t.appUser.id = u.id "
			+ "where u.id = :userId "
			+ "and (t.expired = false or t.revoked = false)")
	List<Token> findAllValidTokens(String userId);
	@Modifying
	@Query(value =  "delete from Token t "
			+ "where (t.expired = true or t.revoked = true)")
	void deleteAllInvalidTokens();
	Optional<Token> findByToken(String token);
}
