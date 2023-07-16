package com.moorabi.reelsapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moorabi.reelsapi.DTO.ReelDTO;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.service.ReelService;

@RestController
@RequestMapping("/api/v1")
public class ReelController {
	
	@Autowired
	private ReelService reelService;
	
	@GetMapping("/reels")
	public List<ReelDTO> getAllReels(){
		return reelService.getAllReels();	
	}
	
	@PostMapping("/reels")
	public ReelDTO createReel(@RequestHeader (name="Authorization") String token,
			@RequestParam("description") String description,
			@RequestParam("country") String country,
			@RequestParam("city") String city,
			@RequestBody MultipartFile file) throws IOException {
		return reelService.createReel(token, new Reel(country,city,description) ,file);
	}
	
	@PostMapping("share/{reel_id}")
	public ReelDTO shareReel(@RequestHeader (name="Authorization") String token,
			@PathVariable(value = "reel_id") long id) throws IOException {
		return reelService.shareReel(token, id);
	}
	
	@GetMapping("reels/users/{user_id}")
	public ResponseEntity<List<ReelDTO>> getReelsByUserId(@PathVariable(value="user_id") String id) throws ResourceNotFoundException{
		return reelService.getReelsByUserId(id);
	}
	
	@GetMapping("reels/{country}")
	public ResponseEntity<List<ReelDTO>> getReelsByCountry(@PathVariable(value="country") String country) throws ResourceNotFoundException{
		return reelService.getReelsByCountry(country);
	}
	
	@GetMapping("reels/{country}/{city}")
	public ResponseEntity<List<ReelDTO>> getReelsByCity(@PathVariable(value="country") String country,@PathVariable(value="city") String city) throws ResourceNotFoundException{
		return reelService.getReelsByCity(country, city);
	}
	
	@GetMapping("reels/{reel_id}")
	public ResponseEntity<ReelDTO> getReelById(@PathVariable(value="reel_id") long reelId) throws ResourceNotFoundException {
		return reelService.getReelById(reelId);
	}
	
	@GetMapping("reelsv/{reel_id}")
	public ResponseEntity<Resource> getVideoReelById(@PathVariable(value="reel_id") long reelId) throws ResourceNotFoundException {
		return reelService.getVideoReelById(reelId);
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
