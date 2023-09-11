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
import com.moorabi.reelsapi.model.AppUser;
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
		List<AppUser> users = userRepository.findAll();
		List<UserDTO> userDTOs = UserUtil.convertAllToDTO(users);
		return userDTOs;
	}
	
	public List<UserDTO> getOnlineUsers(){
		List<AppUser> users = userRepository.findAll().parallelStream().filter(u -> u.getStatus().equals("online")).toList();
		List<UserDTO> userDTOs = UserUtil.convertAllToDTO(users);
		return userDTOs;
	}
	
	public AppUser createUser(AppUser appUser,  Role role) {
//		if(!PasswordValidator.isValid(user.getPassword())) {
//			throw new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid");
//		}
		appUser.getRoles().add(role);
		userRepository.save(appUser);
		return appUser;
	}
	
	public Optional<AppUser> findById(String id) {
        return userRepository.findById(id);
    }
	
	public ResponseEntity<UserDTO> getUserById(String id) throws ResourceNotFoundException{
		AppUser u=userRepository.findById(id)
				.orElseThrow(() -> 
				new ResourceNotFoundException
				("User not found for this id : "+id));
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<UserDTO> getUserByUserName(String userName) throws ResourceNotFoundException {
		AppUser u=userRepository.findUserByUsername(userName);
		if(u==null) {
			throw new ResourceNotFoundException("User not found for this user name : "+userName);
		}
		
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<?> updateUser(String token,AppUser appUser) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		AppUser u=(userRepository.findUserByUsername(userName));
		u.setFirstName(appUser.getFirstName());
		u.setLastName(appUser.getLastName());
		u.setUsername(appUser.getUsername());
		u.setEmail(appUser.getEmail());
		if(!PasswordValidator.isValid(appUser.getPassword())) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid"), 
					HttpStatus.BAD_REQUEST);
		}
		u.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
		userRepository.save(u);
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u));
	}
	
	public ResponseEntity<?> deleteUser(String token) throws ResourceNotFoundException {
		String userName=jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
		AppUser u=(userRepository.findUserByUsername(userName));
		userRepository.deleteById(u.getId());
		return ResponseEntity.ok().build();
	}
	
	public void processOAuthPostLogin(String username,String Email) {
        AppUser existUser = userRepository.findUserByUsername(username);
         
        if (existUser == null) {
            AppUser newUser = new AppUser();
            newUser.setUsername(username);
            newUser.setLoginMethodEnum(LoginMethodEnum.FACEBOOK);
            newUser.setPassword("changeme"); 
            newUser.setEmail(Email);
            userRepository.save(newUser);        
        }
         
    }

	public ResponseEntity<UserDTO> getUserByUserNameOrEmail(String emailOrUsername) throws ResourceNotFoundException {
		if(emailOrUsername.contains("@")) {
			return getUserByEmail(emailOrUsername);
		}else {
			return getUserByUserName(emailOrUsername);
		}
	}

	private ResponseEntity<UserDTO> getUserByEmail(String email) throws ResourceNotFoundException {
		Optional<AppUser> u=userRepository.findByEmail(email);
		if(u.get()==null) {
			throw new ResourceNotFoundException("User not found for this user name : "+ email);
		}
		
		return ResponseEntity.ok().body(UserUtil.convertToDTO(u.get()));
	}  
}
