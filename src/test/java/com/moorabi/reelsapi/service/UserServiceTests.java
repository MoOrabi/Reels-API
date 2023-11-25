package com.moorabi.reelsapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.UserUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserUtil userUtil;
	
	@InjectMocks
	private UserService userService;
	
	
	
	@Test
	void getAllUsersAllReturned() {		
		AppUser user1=new AppUser("km","ll@mdk.xo","kmk"); 
		AppUser user2=new AppUser("kmd","lld@mdk.xo","kmdk"); 
		List<AppUser> listOfUsers= new ArrayList<>();
		listOfUsers.add(user1);
		listOfUsers.add(user2);		
		given(userRepository.findAll()).willReturn(listOfUsers);
		List<UserDTO> allUsers=userService.getAllUsers();
		assertThat(allUsers.size()).isEqualTo(2);
		verify(userRepository,times(1)).findAll();
	}
	
	@Test
	void getUserByIdFound() throws ResourceNotFoundException {
		String id="1";
		Optional<AppUser> user=Optional.of(new AppUser(id, "mo", "Ora", "mtlm", "ndjntj@kk.mkf", "ktll", null, false, null, null, null, false, null, null, null));
		given(userRepository.findById(id)).willReturn(user);
		assertThat(userService.getUserById(id).getBody()).isNotNull();
		assertThat(userService.getUserById(id).getBody().getId()).isEqualTo(id);
	}
	
	@Test
	void getUserByIdNotFound() throws ResourceNotFoundException {
		String id="1";
		given(userRepository.findById(id)).willReturn(null);
		assertThrows(NullPointerException.class,() -> {userService.getUserById(id).getBody();},"User not found for this id : "+id);
	}

}
