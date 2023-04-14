package com.moorabi.reelsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moorabi.reelsapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    

	@Autowired
	private AuthService authService;
	
    public AuthController() {
        
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("user_name") String username,
                                       @RequestParam("password") String password) {
        return authService.loginUser(username, password);
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestParam("first_name") String firstName,
                                      @RequestParam("last_name") String lastName,
                                      @RequestParam("user_name") String userName, 
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password) {
        return authService.saveUser(firstName, lastName, userName, email, password);
    }
}
