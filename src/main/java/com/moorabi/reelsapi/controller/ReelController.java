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

import com.moorabi.reelsapi.exception.NotAllowedException;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class ReelController {
	
	@Autowired
	private ReelRepository reelRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping("/reels")
	public List<Reel> getAllReels(){
		return reelRepository.findAll();
	}
	
	@PostMapping("/reels/{userId}")
	public Reel createReel(@RequestHeader (name="Authorization") String token,
			@RequestBody Reel reel) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=userRepository.findUserByUsername(userName);
		u.getReels().add(reel);
		reel.setUser(u);
		return reelRepository.save(reel);
	}
	
	@GetMapping("reels/users/{user_id}")
	public ResponseEntity<List<Reel>> getReelsByUserId(@PathVariable(value="user_id") long id) throws ResourceNotFoundException{
		User u=userRepository.findById(id).get();
		List<Reel> r=reelRepository.findByUser(u);
		return ResponseEntity.ok().body(r);
	}
	
	@GetMapping("reels/{country}/{city}")
	public ResponseEntity<List<Reel>> getReelsByCity(@PathVariable(value="country") String country,@PathVariable(value="city") String city) throws ResourceNotFoundException{
		List<Reel> r=reelRepository.findReelsByCity(country,city);
		return ResponseEntity.ok().body(r);
	}
	
	@GetMapping("users/{user_id}/reels/{reel_id}")
	public ResponseEntity<Reel> getReelById(@PathVariable(value="reel_id") long reelId) throws ResourceNotFoundException{
		Reel r=reelRepository.findById(reelId).get();
		return ResponseEntity.ok().body(r);
	}
	
	@PutMapping("reels/{reel_id}")
	public ResponseEntity<Reel> updateReel(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reelId,
			@RequestBody Reel reelDetails) throws ResourceNotFoundException, NotAllowedException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reelId).orElseThrow(() -> new ResourceNotFoundException("Reel not found for this id : "+reelId));
		User u=(userRepository.findById(r.getUserId()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			throw new NotAllowedException("Only Owner of reel can update it");
		}
		if(reelDetails.getCountry()!=null)
			r.setCountry(reelDetails.getCountry());
		if(reelDetails.getCity()!=null)
			r.setCity(reelDetails.getCity());
		if(reelDetails.getDescription()!=null)
			r.setDescription(reelDetails.getDescription());
		reelRepository.save(r);
		return ResponseEntity.ok().body(r);
	}
	
	@DeleteMapping("reels/{reel_id}")
	public ResponseEntity<?> deleteReel(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reelId,
			@RequestBody Reel reelDetails) throws ResourceNotFoundException, NotAllowedException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reelId).orElseThrow(() -> new ResourceNotFoundException("Reel not found for this id : "+reelId));
		User u=(userRepository.findById(r.getUserId()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			throw new NotAllowedException("Only Owner of reel can delete it");
		}
		reelRepository.deleteById(reelId);
		return ResponseEntity.ok().build();
	}
}
