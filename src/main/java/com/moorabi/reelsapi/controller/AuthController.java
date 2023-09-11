package com.moorabi.reelsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    

	@Autowired
	private AuthService authService;
	
    public AuthController() {
        
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader (name="Authorization") String token) {
    	return authService.logout(token);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        return authService.loginUserByEmailOrUsername(email, password);
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestParam("first_name") String firstName,
                                      @RequestParam("last_name") String lastName,
                                      @RequestParam("user_name") String userName, 
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password) throws ErrorDetails {
    	AppUser appUser = new AppUser(userName, email, password);
    	appUser.setFirstName(firstName);
    	appUser.setLastName(lastName);
        return authService.registerUser(appUser);
    }
}
