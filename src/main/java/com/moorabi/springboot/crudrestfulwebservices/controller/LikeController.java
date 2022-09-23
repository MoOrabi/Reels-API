package com.moorabi.springboot.crudrestfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;
import com.moorabi.springboot.crudrestfulwebservices.model.Like;
import com.moorabi.springboot.crudrestfulwebservices.model.Reel;
import com.moorabi.springboot.crudrestfulwebservices.model.User;
import com.moorabi.springboot.crudrestfulwebservices.repository.LikeRepository;
import com.moorabi.springboot.crudrestfulwebservices.repository.ReelRepository;
import com.moorabi.springboot.crudrestfulwebservices.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class LikeController {
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikeRepository likeRepository;
	
	
	@GetMapping("/reels/{reel_id}/likes")
	public List<Like> getAllLikesForReel(@PathVariable(value="reel_id") long id){
		Reel reel=reelRepository.findById(id).get();
		return likeRepository.findAllForReel(reel);
	}
	
	@PostMapping("/reels/{reel_id}/users/{user_id}/like")
	public Like postLike(@PathVariable(value="user_id") long user_id,@PathVariable(value="reel_id") long reel_id) {
		Reel r=reelRepository.findById(reel_id).get();
		User u=userRepository.findById(user_id).get();
		Like like=new Like(r, u);
//		if(likeRepository.findLike(r, u)!=null) {
//			r.getLikes().add(like);
//		}
//		reelRepository.save(r);
		return likeRepository.save(like);
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
