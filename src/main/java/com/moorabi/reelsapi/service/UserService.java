package com.moorabi.reelsapi.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;
import com.moorabi.reelsapi.util.UserUtil;
import com.moorabi.reelsapi.validator.PasswordValidator;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	
	@GetMapping("/users")
	public List<UserDTO> getAllUsers(){
		return UserUtil.convertAllToDTO(userRepository.findAll());
	}
	
//	public ResponseEntity<?> createUser(User user) {
//		if(!PasswordValidator.isValid(user.getPassword())) {
//			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid"), HttpStatus.BAD_REQUEST);
//		}
//		userRepository.save(user);
//		return ResponseEntity.ok().body(user);
//	}
	
	public ResponseEntity<UserDTO> getUserById(long id) throws ResourceNotFoundException{
		User u=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id : "+id));
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<UserDTO> getUserByUserName(String userName) throws ResourceNotFoundException {
		User u=userRepository.findUserByUsername(userName);
		if(u==null) {
			throw new ResourceNotFoundException("User not found for this user name : "+userName);
		}
		
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<?> updateUser(String token,User user) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=(userRepository.findUserByUsername(userName));
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setUsername(user.getUsername());
		u.setEmailId(user.getEmailId());
		if(!PasswordValidator.isValid(user.getPassword())) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid"), 
					HttpStatus.BAD_REQUEST);
		}
		u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(u);
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<?> deleteUser(String token) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		User u=(userRepository.findUserByUsername(userName));
		userRepository.deleteById(u.getId());
		return ResponseEntity.ok().build();
	}
}
