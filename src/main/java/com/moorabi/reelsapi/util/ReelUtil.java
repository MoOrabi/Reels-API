package com.moorabi.reelsapi.util;

import java.util.ArrayList;
import java.util.List;
import com.moorabi.reelsapi.DTO.LikeDTO;
import com.moorabi.reelsapi.DTO.ReelDTO;
import com.moorabi.reelsapi.model.Reel;

public class ReelUtil {
	
	
	
	public static ReelDTO convertToDTO(Reel reel) {
		List<LikeDTO> likes=new ArrayList<>();
		if (reel.getLikes()!=null) {
			likes=LikeUtil.convertAllToDTO(reel.getLikes());
		}
		return new ReelDTO((Long)reel.getId(),reel.getUser(),reel.getCountry(),reel.getCity(),reel.getDescription(),reel.getVideoFile(),likes,reel.getOrigin(),reel.getCreatedAt());
	}
	
	public static List<ReelDTO> convertAllToDTO(List<Reel> reels) {
		List<ReelDTO> reelsDTO=new ArrayList<>();
		for (Reel reel : reels) {
			reelsDTO.add(convertToDTO(reel));
		}
		return reelsDTO;
	}
}
