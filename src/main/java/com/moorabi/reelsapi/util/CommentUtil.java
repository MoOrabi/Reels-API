package com.moorabi.reelsapi.util;

import java.util.ArrayList;
import java.util.List;

import com.moorabi.reelsapi.DTO.CommentDTO;
import com.moorabi.reelsapi.model.Comment;

public class CommentUtil {

	public static CommentDTO convertToDTO(Comment comment) {
		
		return new CommentDTO(comment.getId(),comment.getDescription());
	}
	
	public static List<CommentDTO> convertAllToDTO(List<Comment> comments) {
		
		List<CommentDTO> commentsDTO=new ArrayList<>();
		for (Comment comment : comments) {
			CommentDTO commentDTO=convertToDTO(comment);
			commentsDTO.add(commentDTO);
		}
		
		return commentsDTO;
	}
}
