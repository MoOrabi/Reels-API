package com.moorabi.reelsapi.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.moorabi.reelsapi.DTO.LikeDTO;
import com.moorabi.reelsapi.model.Like;

public class LikeUtil {

	public static LikeDTO convertToDTO(Like like) {
		
		return new LikeDTO(like.getId(),like.getUser());
	}
	
	public static List<LikeDTO> convertAllToDTO(List<Like> list) {
		
		List<LikeDTO> likesDTO=new ArrayList<>();
		for (Like like : list) {
			LikeDTO likeDTO=new LikeDTO(like.getId(),like.getUser());
			likesDTO.add(likeDTO);
		}
		
		return likesDTO;
	}
}
