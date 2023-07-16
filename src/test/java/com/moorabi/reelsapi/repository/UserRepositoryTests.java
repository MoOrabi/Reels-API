package com.moorabi.reelsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.moorabi.reelsapi.model.AppUser;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	AppUser appUser;
	
	@BeforeAll
	void saveUser() {
		appUser = new AppUser("informatikerm","informatikerm@gmail.com","Password1234");
		userRepository.save(appUser);
	}
	
	@Test
	void UserRepository_FindUserByUserName_UserReturned() {
		String userName=appUser.getUsername();
		AppUser foundUser=userRepository.findUserByUsername(userName);
		
		assertThat(foundUser).isNotNull();
		assertThat(foundUser.getUsername()).isEqualTo(userName);
	}
	
	@Test
	void UserRepository_FindUserByUserName_UserNotFound() {
		String userName=appUser.getUsername()+"1";;
		AppUser foundUser=userRepository.findUserByUsername(userName);
		
		assertThat(foundUser).isNull();
	}
	
	@Test
	void UserRepository_FindByEmail_UserReturned() {
		String email=appUser.getEmail();
		Optional<AppUser> foundUser=userRepository.findByEmail(email);
		
		assertThat(foundUser.get()).isNotNull();
		assertThat(foundUser.get().getEmail()).isEqualTo(email);
	}
	
	@Test
	void UserRepository_FindByEmail_EmailNotFound() {
		String email=appUser.getEmail()+"1";
		Optional<AppUser> foundUser=userRepository.findByEmail(email);
		
		assertFalse(foundUser.isPresent());
	}
	
	@Test
	void UserRepository_FindById_UserReturned() {
		String id= appUser.getId();
		Optional<AppUser> foundUser=userRepository.findById(id);
		
		assertThat(foundUser.get()).isNotNull();
		assertThat(foundUser.get().getId()).isEqualTo(id);
	}
	
	@Test
	void UserRepository_FindById_IdNotFound() {
		String id= appUser.getId();
		Optional<AppUser> foundUser=userRepository.findById(id+"1");
		
		assertFalse(foundUser.isPresent());
	}
	
	
}
