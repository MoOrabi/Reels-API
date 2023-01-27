package com.moorabi.reelsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@Service
public class ReelService {
	
	@Autowired
	private ReelRepository reelRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public List<Reel> getAllReels(){
		return reelRepository.findAll();
	}
	
	public Reel createReel(String token,Reel reel) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=userRepository.findUserByUsername(userName);
		u.getReels().add(reel);
		reel.setUser(u);
		return reelRepository.save(reel);
	}
	
	public ResponseEntity<List<Reel>> getReelsByUserId(long id) throws ResourceNotFoundException{
		User u=userRepository.findById(id).get();
		List<Reel> r=reelRepository.findByUser(u);
		return ResponseEntity.ok().body(r);
	}
	
	public ResponseEntity<List<Reel>> getReelsByCity(String country,String city) throws ResourceNotFoundException{
		List<Reel> r=reelRepository.findReelsByCity(country,city);
		return ResponseEntity.ok().body(r);
	}
	
	public ResponseEntity<Reel> getReelById(long reelId) throws ResourceNotFoundException{
		Reel r=reelRepository.findById(reelId).get();
		return ResponseEntity.ok().body(r);
	}
	
	public ResponseEntity<?> updateReel(String token,long reelId,Reel reelDetails) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reelId).orElseThrow(() -> new ResourceNotFoundException("Reel not found for this id : "+reelId));
		User u=(userRepository.findById(r.getUserId()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.NOT_ALLOWED,"Only Owner of reel can delete it"),HttpStatus.UNAUTHORIZED);
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
	
	public ResponseEntity<?> deleteReel(String token,long reelId,Reel reelDetails) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reelId).orElseThrow(() -> new ResourceNotFoundException("Reel not found for this id : "+reelId));
		User u=(userRepository.findById(r.getUserId()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.NOT_ALLOWED,"Only Owner of reel can delete it"),HttpStatus.UNAUTHORIZED);
		}
		reelRepository.deleteById(reelId);
		return ResponseEntity.ok().build();
	}
}
