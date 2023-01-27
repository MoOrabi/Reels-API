package com.moorabi.reelsapi.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public ResponseEntity<User> getUserById(long id) throws ResourceNotFoundException{
		User u=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id : "+id));
		return ResponseEntity.ok().body(u);
	}
	
	public ResponseEntity<User> getUserByUserName(String userName) throws ResourceNotFoundException {
		User u=userRepository.findUserByUsername(userName);
		if(u==null) {
			throw new ResourceNotFoundException("User not found for this user name : "+userName);
		}
		
		return ResponseEntity.ok().body(u);
	}
	
	public ResponseEntity<User> updateUser(String token,User user) throws ResourceNotFoundException {
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
	
	public ResponseEntity<?> deleteUser(String token) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=(userRepository.findUserByUsername(userName));
		userRepository.deleteById(u.getId());
		return ResponseEntity.ok().build();
	}
}
