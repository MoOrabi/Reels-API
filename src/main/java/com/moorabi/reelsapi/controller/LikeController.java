package com.moorabi.reelsapi.controller;

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

import com.moorabi.reelsapi.DTO.LikeDTO;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.service.LikeService;

@RestController
@RequestMapping("/api/v1")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@GetMapping("/reels/{reel_id}/likes")
	public List<LikeDTO> getAllLikesForReel(@PathVariable(value="reel_id") long id){
		return likeService.getAllLikesForReel(id);
	}
	
	@PostMapping("/reels/{reel_id}/like")
	public ResponseEntity<?> postLike(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id) throws ResourceNotFoundException {
		
		return likeService.deleteLike(token, reel_id);
	}


	
	@DeleteMapping("/reels/{reel_id}/like")
	public ResponseEntity<?> deleteLike(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id) throws ResourceNotFoundException {
		return likeService.deleteLike(token, reel_id);
	}

}
