package com.moorabi.reelsapi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.model.Token;
import com.moorabi.reelsapi.model.TokenType;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.TokenRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;
import com.moorabi.reelsapi.validator.PasswordValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    protected final Log logger = LogFactory.getLog(getClass());
    public static final String FACEBOOK_AUTH_URL = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=%s";

    final UserRepository userRepository;
    final TokenService tokenService;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    
//    final WebClient webClient;

    
    
    public AuthService(UserRepository userRepository,
    		TokenService tokenService,
    		AuthenticationManager authenticationManager,
            JwtUserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    
    public ResponseEntity<?> registerUser(AppUser appUser) throws ErrorDetails {
		if(!PasswordValidator.isValid(appUser.getPassword())) {
			throw new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid");
		}
		if(userRepository.findUserByUsername(appUser.getUsername())!=null) {
			throw new ErrorDetails(Errors.BAD_REQUEST,"You Already Have an Account Here");
		}
		
		Map<String, Object> responseMap = new HashMap<>();
		appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
		appUser.setAuthorities("USER");
		UserDetails userDetails = userDetailsService.createUserDetails(appUser.getUsername(), appUser.getPassword());
		String jwtToken = jwtTokenUtil.generateToken(userDetails);
		appUser.setStatus("online");
		userRepository.save(appUser);
		tokenService.saveUserToken(appUser, jwtToken);
		responseMap.put("error", false);
		responseMap.put("username", appUser.getUsername());
		responseMap.put("message", "Account created successfully");
		responseMap.put("token", jwtToken);
		return ResponseEntity.ok(responseMap);
	}
    
    public ResponseEntity<?> logout(String token) {
    	String tokenWithoutBearer = token.split(" ")[1];
    	String userName = jwtTokenUtil.getUsernameFromToken(tokenWithoutBearer);
    	AppUser appUser = userRepository.findUserByUsername(userName);
    	Map<String, Object> responseMap = new HashMap<>();    	
    	if(appUser.getStatus().equals("online")) {
    		appUser.setStatus("offline");
    		userRepository.save(appUser);
    		jwtTokenUtil.revokeAllUserTokens(appUser);
    		responseMap.put("Logout", "Sucessful Logout");
    		return ResponseEntity.ok(responseMap);
    	}else {
			responseMap.put("error", true);
            responseMap.put("message", "User is not active");
            return ResponseEntity.status(500).body(responseMap);
		}
    	
    }
    
    public ResponseEntity<?> loginUser(String username,
                                       String password) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username
                    , password));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                AppUser appUser = userRepository.findUserByUsername(username);
                jwtTokenUtil.revokeAllUserTokens(appUser);
                String jwtToken = jwtTokenUtil.generateToken(appUser);
                tokenService.saveUserToken(appUser, jwtToken);
                appUser.setStatus("online");
                userRepository.save(appUser);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", jwtToken);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(500).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }
    
    public ResponseEntity<?> loginUserByEmail(String email,
            String password) {
	Map<String, Object> responseMap = new HashMap<>();
	try {
//	Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email
//	, password));
	if (true) {
	logger.info("Logged In");
	Optional<AppUser> appUser = userRepository.findByEmail(email);
	jwtTokenUtil.revokeAllUserTokens(appUser.get());
	String jwtToken = jwtTokenUtil.generateToken(appUser.get());
	tokenService.saveUserToken(appUser.get(), jwtToken);
	appUser.get().setStatus("online");
	userRepository.save(appUser.get());
	responseMap.put("error", false);
	responseMap.put("message", "Logged In");
	responseMap.put("token", jwtToken);
	return ResponseEntity.ok(responseMap);
	} else {
	responseMap.put("error", true);
	responseMap.put("message", "Invalid Credentials");
	return ResponseEntity.status(401).body(responseMap);
	}
	} catch (DisabledException e) {
	e.printStackTrace();
	responseMap.put("error", true);
	responseMap.put("message", "User is disabled");
	return ResponseEntity.status(500).body(responseMap);
	} catch (BadCredentialsException e) {
	responseMap.put("error", true);
	responseMap.put("message", "Invalid Credentials");
	return ResponseEntity.status(401).body(responseMap);
	} catch (Exception e) {
	e.printStackTrace();
	responseMap.put("error", true);
	responseMap.put("message", "Something went wrong");
	return ResponseEntity.status(500).body(responseMap);
	}
}


	public ResponseEntity<?> loginUserByEmailOrUsername(String email, String password) {
		if(email.contains("@")) {
			return loginUserByEmail(email, password);
		}else {
			return loginUser(email, password);
		}
	}
    
}
