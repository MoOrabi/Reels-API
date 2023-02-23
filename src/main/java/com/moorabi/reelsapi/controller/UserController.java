package com.moorabi.reelsapi.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
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
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService userService;
	
	@GetMapping("/users")
	public List<UserDTO> getAllUsers(){
		return userService.getAllUsers();
	}
	
//	@PostMapping("/users")
//	public User createUser(@RequestBody User user) {
//		userService.createUser(user);
//		return user;
//	}
	
	@GetMapping("users/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value="id") long id) throws ResourceNotFoundException{
		return userService.getUserById(id);
	}
	
	@GetMapping("users/{userName}")
	public ResponseEntity<UserDTO> getUserByUserName(@PathVariable(value="userName") String userName) throws ResourceNotFoundException{
		return userService.getUserByUserName(userName);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestHeader (name="Authorization") String token
			,@RequestBody User user) throws ResourceNotFoundException {
		return userService.updateUser(token, user);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser(@RequestHeader (name="Authorization") String token) throws ResourceNotFoundException {
		return userService.deleteUser(token);
	}
}
