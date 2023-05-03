package com.moorabi.reelsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.exception.ResourceNotFoundException;
import com.moorabi.reelsapi.model.LoginMethodEnum;
import com.moorabi.reelsapi.model.Role;
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

	
	public List<UserDTO> getAllUsers(){
		return UserUtil.convertAllToDTO(userRepository.findAll());
	}
	
	public User createUser(User user,  Role role) {
//		if(!PasswordValidator.isValid(user.getPassword())) {
//			throw new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid");
//		}
		user.getRoles().add(role);
		userRepository.save(user);
		return user;
	}
	
	public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
	
	public ResponseEntity<UserDTO> getUserById(String id) throws ResourceNotFoundException{
		User u=userRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException
				("User not found for this id : "+id));
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
		u.setEmail(user.getEmail());
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
	
	public void processOAuthPostLogin(String username,String Email) {
        User existUser = userRepository.findUserByUsername(username);
         
        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setLoginMethodEnum(LoginMethodEnum.FACEBOOK);
            newUser.setPassword("changeme"); 
            newUser.setEmail(Email);
            userRepository.save(newUser);        
        }
         
    }  
}
