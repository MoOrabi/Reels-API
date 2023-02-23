package com.moorabi.reelsapi.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.model.User;

public class UserUtil {
	
	public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
	
	public static UserDTO convertToDTO(User user) {
		return new UserDTO((Long)user.getId(),user.getEmailId(),user.getPassword());
	}

	public static List<UserDTO> convertAllToDTO(List<User> users){
		List<UserDTO> usersDTO=new ArrayList<>();
		for (User user : users) {
			usersDTO.add(convertToDTO(user));
		}
		return null;
	}

}
