package com.moorabi.reelsapi.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Like;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.LikeRepository;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class LikeController {
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
//	@Autowired
//	private ErrorDetails errorDetails;
	
	
	@GetMapping("/reels/{reel_id}/likes")
	public List<Like> getAllLikesForReel(@PathVariable(value="reel_id") long id){
		Reel reel=reelRepository.findById(id).get();
		return likeRepository.findAllForReel(reel);
	}
	
	@PostMapping("/reels/{reel_id}/like")
	public Like postLike(@PathVariable(value="reel_id") long reel_id,
			@RequestHeader (name="Authorization") String token) throws Exception {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		
		Reel r=reelRepository.findById(reel_id).get();
		User u=userRepository.findUserByUsername(userName);
		Like like=new Like(r, u);
		if(likeRepository.findLike(r, u)==null) {
			likeRepository.save(like);
			r.getLikes().add(like);
			reelRepository.save(r);
			return like;
		}else {
			throw new ErrorDetails(LocalDate.now(),"Duplicated Like","You already liked these reel");
		}
		
	}


	
	@DeleteMapping("/reels/{reel_id}/users/{user_id}/like")
	public ResponseEntity<?> deleteLike(@PathVariable(value="user_id") long user_id,@PathVariable(value="reel_id") long reel_id) throws ResourceNotFoundException {
		Reel r=reelRepository.findById(reel_id).get();
		User u=userRepository.findById(user_id).get();
		if(likeRepository.findLike(r, u)!=null) {
			Like l=likeRepository.findLike(r, u);
			likeRepository.delete(l);
		}
		
		return ResponseEntity.ok().build();
	}

}
