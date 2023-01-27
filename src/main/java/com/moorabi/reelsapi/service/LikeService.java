package com.moorabi.reelsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Like;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.LikeRepository;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@Service
public class LikeService {
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public List<Like> getAllLikesForReel(long id){
		Reel reel=reelRepository.findById(id).get();
		return likeRepository.findAllForReel(reel);
	}
	
	public ResponseEntity<?> postLike(String token,long reel_id) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		
		Reel r=reelRepository.findById(reel_id).get();
		User u=userRepository.findUserByUsername(userName);
		Like like=new Like(r, u);
		if(likeRepository.findLike(r, u)==null) {
			likeRepository.save(like);
			return ResponseEntity.ok(like);
		}else {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.BAD_REQUEST,"You Already Liked This"),HttpStatus.UNAUTHORIZED);
					
		}
		
	}


	
	public ResponseEntity<?> deleteLike(String token,long reel_id) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reel_id).get();
		User u=userRepository.findUserByUsername(userName);
		if(likeRepository.findLike(r, u)!=null) {
			Like l=likeRepository.findLike(r, u);
			likeRepository.delete(l);
		}else {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.NOT_FOUND,"You didn't like that reel"),HttpStatus.UNAUTHORIZED);
		}
		
		return ResponseEntity.ok().build();
	}

}
