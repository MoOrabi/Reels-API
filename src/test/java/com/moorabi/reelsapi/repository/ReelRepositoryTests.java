package com.moorabi.reelsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.Reel;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReelRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReelRepository reelRepository;
	
	AppUser appUser;
	
	AppUser appUser1;
	
	AppUser appUser2;
	
	Reel reel;
	
	Reel reel1;
	
	Reel reel2;
	
	@BeforeAll
	void setUp() throws IOException {
		appUser = new AppUser("informatikerm","informatikerm@gmail.com","Password1234");
		appUser1 = new AppUser("informatikerm1","informatikerm1@gmail.com","Password12341");
		userRepository.save(appUser);
		userRepository.save(appUser1);
		appUser2 = new AppUser("informatikerm2","informatikerm2@gmail.com","Password12342");
		userRepository.save(appUser2);
		File videoFile = new File("C:\\Users\\infor\\Downloads\\General\\DoaaSeminar\\DSC_7659.JPG") ;
		byte[] video= FileUtils.readFileToByteArray(videoFile);
		reel = new Reel(appUser, "Egypt", "Cairo", "First Reel", video);
		reelRepository.save(reel);
		
		reel1 = new Reel(appUser, "Austria", "Vienna", "Second Reel", video);
		reelRepository.save(reel1);
		
		reel2 = new Reel(appUser1, "Egypt", "Cairo", "Third Reel", video);
		reelRepository.save(reel2);
	}
	
	@Test
	void findReelsForUserByUserIdAllFound() {
		List<Reel> foundReelsForUser = reelRepository.findReelsForUserByUserId(appUser.getId());
		assertThat(foundReelsForUser.size()).isEqualTo(2);
	}
	
	@Test
	void findReelsForUserByUserIdNoneFound() {
		List<Reel> foundReelsForUser = reelRepository.findReelsForUserByUserId(appUser2.getId());
		assertThat(foundReelsForUser.size()).isEqualTo(0);
	}
	
	@Test
	void findReelsByCountryAndCity() {
		List<Reel> foundReelsForUser = reelRepository.findByCountryAndCity(reel.getCountry(),reel.getCity());
		assertThat(foundReelsForUser.size()).isEqualTo(2);
	}
	
	@Test
	void findReelsByCountry() {
		List<Reel> foundReelsForUser = reelRepository.findByCountry(reel1.getCountry());
		assertThat(foundReelsForUser.size()).isEqualTo(1);
	}
}
