package com.moorabi.reelsapi.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.Like;
import com.moorabi.reelsapi.model.Reel;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LikeRepositoryTests {

	@Autowired
	private LikeRepository likeRepository;
	
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
		
		Like like1 = new Like(reel, appUser); 
		Like like2 = new Like(reel, appUser1);
		
		likeRepository.save(like1);
		likeRepository.save(like2);
		List<Like> foundLikes=likeRepository.findAllForReel(reel);
		assertThat(foundLikes.size()).isEqualTo(2);
		
	}
	
	@Test
	void findAllForReel_NoneFound() {
		
		List<Like> foundLikes=likeRepository.findAllForReel(reel);
		assertThat(foundLikes.size()).isEqualTo(0);
		
	}
	
	@Test
	void findLikeFound() {
		
		Like like1 = new Like(reel, appUser);
		likeRepository.save(like1);
		Like found=likeRepository.findLike(reel, appUser);
		assertThat(found).isNotNull();
		assertThat(found.getReelId()).isEqualTo(reel.getId());
		assertThat(found.getUserName()).isEqualTo(appUser.getUsername());
	}
	
	@Test
	void findLikeNotFound() {
		
		Like found=likeRepository.findLike(reel, appUser);
		assertThat(found).isNull();
		
	}
	
	@Test
	void deleteLikeOneDeleted() {
		Like like1 = new Like(reel, appUser);
		likeRepository.save(like1);
		
		int deleted = likeRepository.deleteLike(reel, appUser);
		assertThat(deleted).isEqualTo(1);
		assertThat(likeRepository.findLike(reel, appUser)).isNull();
	}
	
	
	@Test
	void deleteLikeNoLikeFound() {
		
		int deleted = likeRepository.deleteLike(reel, appUser);
		assertThat(deleted).isEqualTo(0);
	}
	
}
