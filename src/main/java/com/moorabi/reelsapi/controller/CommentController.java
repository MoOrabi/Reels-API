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

import com.moorabi.reelsapi.DTO.CommentDTO;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.Comment;
import com.moorabi.reelsapi.service.CommentService;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/reels/{reel_id}/comments")
	public List<CommentDTO> getAllCommentsForReel(@PathVariable(value="reel_id") long id){
		return commentService.getAllCommentsForReel(id);	
	}
	
	@PostMapping("/reels/{reel_id}/comment")
	public CommentDTO createComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,@RequestBody Comment comment) {
		return commentService.createComment(token, reel_id, comment);
	}


	@PutMapping("/reels/{reel_id}/comments/{comment_id}")
	public ResponseEntity<?> updateComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,
			@PathVariable(value="comment_id") long comment_id,
			@RequestBody Comment commentDetails) throws ResourceNotFoundException {
		return commentService.deleteComment(token, reel_id, comment_id);	
	}
	
	@DeleteMapping("/reels/{reel_id}/comments/{comment_id}")
	public ResponseEntity<?> deleteComment(@RequestHeader (name="Authorization") String token,
			@PathVariable(value="reel_id") long reel_id,
			@PathVariable(value="comment_id") long comment_id) throws ResourceNotFoundException {
		return commentService.deleteComment(token, reel_id, comment_id);
	}

}
