package com.moorabi.reelsapi.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value="id") long id) throws ResourceNotFoundException{
		User u=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id : "+id));
		return ResponseEntity.ok().body(u);
	}
	
	@GetMapping("users/{userName}")
	public ResponseEntity<User> getUserByUserName(@PathVariable(value="userName") String userName) throws ResourceNotFoundException{
		User u=userRepository.findUserByUsername(userName);
		if(u==null) {
			throw new ResourceNotFoundException("User not found for this user name : "+userName);
		}
		
		return ResponseEntity.ok().body(u);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestHeader (name="Authorization") String token
			,@RequestBody User user) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=(userRepository.findUserByUsername(userName));
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setUsername(user.getUsername());
		u.setEmailId(user.getEmailId());
		u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(u);
		return ResponseEntity.ok().body(u);
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser(@RequestHeader (name="Authorization") String token) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=(userRepository.findUserByUsername(userName));
		userRepository.deleteById(u.getId());
		return ResponseEntity.ok().build();
	}
}
