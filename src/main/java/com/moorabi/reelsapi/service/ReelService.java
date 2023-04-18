package com.moorabi.reelsapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moorabi.reelsapi.DTO.ReelDTO;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;
import com.moorabi.reelsapi.util.ReelUtil;

@Service
public class ReelService {
	
	@Autowired
	private ReelRepository reelRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public List<ReelDTO> getAllReels(){
		return ReelUtil.convertAllToDTO(reelRepository.findAll());
	}
	
	public ReelDTO createReel(String token,Reel reel, MultipartFile file) throws IOException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=userRepository.findUserByUsername(userName);
		u.getReels().add(reel);
		reel.setUser(u);
		reel.setVideoFile(file.getBytes());
		reelRepository.save(reel);
		return ReelUtil.convertToDTO(reel);
	}
	
	public ResponseEntity<List<ReelDTO>> getReelsByUserId(long id) throws ResourceNotFoundException{
		List<Reel> r=reelRepository.findByUserId(id);
		return ResponseEntity.ok().body(ReelUtil.convertAllToDTO(r));
	}
	
	public ResponseEntity<List<ReelDTO>> getReelsByCountry(String country) throws ResourceNotFoundException{
		List<Reel> r=reelRepository.findReelsByCountry(country);
		return ResponseEntity.ok().body(ReelUtil.convertAllToDTO(r));
	}
	
	public ResponseEntity<List<ReelDTO>> getReelsByCity(String country,String city) throws ResourceNotFoundException{
		List<Reel> r=reelRepository.findReelsByCity(country,city);
		return ResponseEntity.ok().body(ReelUtil.convertAllToDTO(r));
	}
	
	public ResponseEntity<ReelDTO> getReelById(long reelId) throws ResourceNotFoundException{
		Reel r=reelRepository.findById(reelId).get();
		return ResponseEntity.ok().body(ReelUtil.convertToDTO(r));
	}
	
	public ResponseEntity<Resource> getVideoReelById(long reelId) throws ResourceNotFoundException{
		Reel r=reelRepository.findById(reelId).get();
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(new ByteArrayResource(ReelUtil.convertToDTO(r).getVideoFile()));
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
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(ReelUtil.convertToDTO(r));
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

	public ReelDTO shareReel(String token, long id) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel  reel=reelRepository.findById(id).get();
		User u=userRepository.findUserByUsername(userName);
		Reel newReel= new Reel(u, reel.getCountry(), reel.getCity(), reel.getDescription(),reel.getVideoFile());
		u.getReels().add(newReel);
		newReel.setOrigin(reel);
		reelRepository.save(newReel);
		return ReelUtil.convertToDTO(newReel);
	}
}
