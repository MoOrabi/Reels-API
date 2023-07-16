package com.moorabi.reelsapi.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.UserRepository;

public class UserUtil {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUser) {
            return ((AppUser) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
	
	public static UserDTO convertToDTO(AppUser appUser) {
		return new UserDTO((String)appUser.getId(),appUser.getEmail(),appUser.getPassword());
	}

	public static List<UserDTO> convertAllToDTO(List<AppUser> appUsers){
		List<UserDTO> usersDTO=new ArrayList<>();
		for (AppUser appUser : appUsers) {
			usersDTO.add(convertToDTO(appUser));
		}
		return usersDTO;
	}
	
	private @NotBlank @Size(max = 50) String generateUserName(String firstName, String lastName) {
		long count=1;
		while(userRepository.findUserByUsername(firstName+lastName+count) !=null) {
			count++;
		}
		return firstName+lastName+count;
	}

}
