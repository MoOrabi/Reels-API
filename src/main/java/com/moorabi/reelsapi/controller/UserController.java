package com.moorabi.reelsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<UserDTO> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/onlineusers")
	public List<UserDTO> getOnlineUsers(){
		return userService.getOnlineUsers();
	}
	
//	@PostMapping("/users")
//	public User createUser(@RequestBody User user) {
//		userService.createUser(user);
//		return user;
//	}
	
	@GetMapping("users/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value="id") String id) throws ResourceNotFoundException{
		return userService.getUserById(id);
	}
//	public ResponseEntity<User> getUserById(@PathVariable(value="id") long id) throws ResourceNotFoundException{
//		System.out.println("Hi");
//		return userService.getUserById(id);
//	}
	@GetMapping("users/un/{email}")
	public ResponseEntity<UserDTO> getUserByUserName(@PathVariable(value="email") String emailOrUsername) throws ResourceNotFoundException{
		return userService.getUserByUserNameOrEmail(emailOrUsername);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestHeader (name="Authorization") String token
			,@RequestBody AppUser appUser) throws ResourceNotFoundException {
		return userService.updateUser(token, appUser);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser(@RequestHeader (name="Authorization") String token) throws ResourceNotFoundException {
		return userService.deleteUser(token);
	}
}
