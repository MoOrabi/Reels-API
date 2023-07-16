package com.moorabi.reelsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.model.Reel;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentRepositoryTests {

	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReelRepository reelRepository;
	
	AppUser appUser;
	
	AppUser appUser1;
	
	Reel reel;
	
	@BeforeAll
	void setUp() throws IOException {
		appUser = new AppUser("informatikerm","informatikerm@gmail.com","Password1234");
		appUser1 = new AppUser("informatikerm1","informatikerm1@gmail.com","Password12341");
		userRepository.save(appUser);
		userRepository.save(appUser1);
		File videoFile = new File("C:\\Users\\infor\\Downloads\\General\\DoaaSeminar\\DSC_7659.JPG") ;
		byte[] video= FileUtils.readFileToByteArray(videoFile);
		reel = new Reel(appUser, "Egypt", "Cairo", "First Reel", video);
		reelRepository.save(reel);
	}
	
	@Test
	void findAllForReel_allReturned() {
		
		Comment comment = new Comment("Nice One", reel, appUser); 
		Comment comment2 = new Comment("Nice One2", reel, appUser); 
		Comment comment1 = new Comment("Nice One whoo", reel, appUser1); 
		
		commentRepository.save(comment);
		commentRepository.save(comment1);
		commentRepository.save(comment2);
		List<Comment> foundComments=commentRepository.findAllForReel(reel);
		assertThat(foundComments.size()).isEqualTo(3);
		List<Comment> foundCommentsForFirstUser=foundComments.stream()
				.filter(c -> c.getUserName()==appUser.getUsername())
				.collect(Collectors.toList());
		assertThat(foundCommentsForFirstUser.size()).isEqualTo(2);
	}
	
	@Test
	void findAllForReel_NoneFound() {
		
		List<Comment> foundComments=commentRepository.findAllForReel(reel);
		assertThat(foundComments.size()).isEqualTo(0);
		
	}
}
