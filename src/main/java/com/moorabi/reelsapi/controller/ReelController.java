package com.moorabi.reelsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.service.ReelService;

@RestController
@RequestMapping("/api/v1")
public class ReelController {
	
	@Autowired
	private ReelService reelService;
	
	@GetMapping("/reels")
	public List<Reel> getAllReels(){
		return reelService.getAllReels();	
	}
	
	@PostMapping("/reels")
	public Reel createReel(@RequestHeader (name="Authorization") String token,
			@RequestBody Reel reel) {
		return reelService.createReel(token, reel);
	}
	
	@GetMapping("reels/users/{user_id}")
	public ResponseEntity<List<Reel>> getReelsByUserId(@PathVariable(value="user_id") long id) throws ResourceNotFoundException{
		return reelService.getReelsByUserId(id);
	}
	
	@GetMapping("reels/{country}/{city}")
	public ResponseEntity<List<Reel>> getReelsByCity(@PathVariable(value="country") String country,@PathVariable(value="city") String city) throws ResourceNotFoundException{
		return reelService.getReelsByCity(country, city);
	}
	
	@GetMapping("users/{user_id}/reels/{reel_id}")
	public ResponseEntity<Reel> getReelById(@PathVariable(value="reel_id") long reelId) throws ResourceNotFoundException {
		return reelService.getReelById(reelId);
	}
	
	@PutMapping("reels/{reel_id}")
	public ResponseEntity<?> updateReel(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reelId,
			@RequestBody Reel reelDetails) throws ResourceNotFoundException{
		return reelService.updateReel(token, reelId, reelDetails);
	}
	
	@DeleteMapping("reels/{reel_id}")
	public ResponseEntity<?> deleteReel(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reelId,
			@RequestBody Reel reelDetails) throws ResourceNotFoundException {
		return reelService.deleteReel(token, reelId, reelDetails);
	}
}
