package com.moorabi.reelsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.NotAllowedException;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.CommentRepository;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping("/reels/{reel_id}/comments")
	public List<Comment> getAllCommentsForReel(@PathVariable(value="reel_id") long id){
		Reel reel=reelRepository.findById(id).get();
		return commentRepository.findAllForReel(reel);
	}
//	
	@PostMapping("/reels/{reel_id}/comment")
	public Comment createComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,@RequestBody Comment comment) {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		Reel r=reelRepository.findById(reel_id).get();
		comment.setReel(r);
		comment.setUser(userRepository.findUserByUsername(userName));
		return commentRepository.save(comment);
	}


	@PutMapping("/reels/{reel_id}/comments/{comment_id}")
	public ResponseEntity<?> updateComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,
			@PathVariable(value="comment_id") long comment_id,
			@RequestBody Comment commentDetails) throws ResourceNotFoundException, NotAllowedException {
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
	
	@DeleteMapping("/reels/{reel_id}/comments/{comment_id}")
	public ResponseEntity<?> deleteComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,
			@PathVariable(value="comment_id") long comment_id) throws ResourceNotFoundException, NotAllowedException {
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
