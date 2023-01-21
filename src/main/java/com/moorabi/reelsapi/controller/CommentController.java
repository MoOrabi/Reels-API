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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.CommentRepository;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	@Autowired
	private ReelRepository reelRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("/reels/{reel_id}")
	public List<Comment> getAllCommentsForReel(@PathVariable(value="reel_id") long id){
		Reel reel=reelRepository.getById(id);
		return commentRepository.findAllForReel(reel);
	}
//	
	@PostMapping("/reels/{reel_id}/users/{user_id}")
	public Comment createComment(@PathVariable(value="user_id") long user_id,@PathVariable(value="reel_id") long reel_id,@RequestBody Comment comment) {
		Reel r=reelRepository.findById(reel_id).get();
		r.getComments().add(comment);
		comment.setReel(r);
		comment.setUser(userRepository.findById(user_id).get());
		reelRepository.save(r);
		return commentRepository.save(comment);
	}


	@PutMapping("/reels/{reel_id}/users/{user_id}/comments/{comment_id}")
	public ResponseEntity<Comment> updateComment(@PathVariable(value="user_id") long user_id,@PathVariable(value="reel_id") long reel_id,@PathVariable(value="comment_id") long comment_id,@RequestBody Comment commentDetails) throws ResourceNotFoundException {
		Comment c= commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id : "+comment_id));
//		Reel r=reelRepository.findById(reel_id).get();
		if(commentDetails.getDescription()!=null)
			c.setDescription(commentDetails.getDescription());
		commentRepository.save(c);
		return ResponseEntity.ok().body(c);
	}
	
	@DeleteMapping("/reels/{reel_id}/users/{user_id}/comments/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable(value="user_id") long user_id,@PathVariable(value="reel_id") long reel_id,@PathVariable(value="comment_id") long comment_id) throws ResourceNotFoundException {
		Comment c= commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id : "+comment_id));
//		Reel r=reelRepository.findById(reel_id).get();
		commentRepository.deleteById(comment_id);
		return ResponseEntity.ok().build();
	}

}
