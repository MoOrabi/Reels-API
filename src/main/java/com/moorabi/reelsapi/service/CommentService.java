package com.moorabi.reelsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.CommentRepository;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@Service
public class CommentService {
	
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public List<Comment> getAllCommentsForReel( long id){
		Reel reel=reelRepository.findById(id).get();
		return commentRepository.findAllForReel(reel);
	}

	public Comment createComment(String token,long reel_id,Comment comment) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reel_id).get();
		comment.setReel(r);
		comment.setUser(userRepository.findUserByUsername(userName));
		return commentRepository.save(comment);
	}


	public ResponseEntity<?> updateComment(String token,long reel_id,long comment_id,Comment commentDetails) 
			throws ResourceNotFoundException{
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Comment c= commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id : "+comment_id));
		User u=(userRepository.findById(c.getUser()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.NOT_ALLOWED,"Only Owner of comment can update it"),HttpStatus.UNAUTHORIZED);
		}
		Reel r=reelRepository.findById(reel_id).get();
		if(commentDetails.getDescription()!=null)
			c.setDescription(commentDetails.getDescription());
		commentRepository.save(c);
		reelRepository.save(r);
		return ResponseEntity.ok().body(c);
	}
	
	public ResponseEntity<?> deleteComment(String token,long reel_id,long comment_id) 
			throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);

		Comment c= commentRepository.findById(comment_id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id : "+comment_id));
		User u=(userRepository.findById(c.getUser()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this user name : "+userName));;
		if(!u.getUsername().equals(userName)) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.NOT_ALLOWED,"Only Owner of comment can delete it"),HttpStatus.UNAUTHORIZED);
		}
		commentRepository.deleteById(comment_id);
		return ResponseEntity.ok().build();
	}

}