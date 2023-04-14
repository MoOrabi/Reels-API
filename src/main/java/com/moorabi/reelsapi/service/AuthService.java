package com.moorabi.reelsapi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
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
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.repository.TokenRepository;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;
import com.moorabi.reelsapi.validator.PasswordValidator;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
public class AuthService {

    protected final Log logger = LogFactory.getLog(getClass());
    public static final String FACEBOOK_AUTH_URL = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=%s";

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    
//    final WebClient webClient;

    public AuthService(UserRepository userRepository,
    		TokenRepository tokenRepository,
    		AuthenticationManager authenticationManager,
            JwtUserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<?> loginUser(String username,
                                       String password) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username
                    , password));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                User user = userRepository.findUserByUsername(username);
                String jwtToken = jwtTokenUtil.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
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
    
    public ResponseEntity<?> saveUser(String firstName,
                                      String lastName,
                                      String userName, 
                                      String email,
                                      String password) {
    	if(!PasswordValidator.isValid(password)) {
			return new ResponseEntity<ErrorDetails>(new ErrorDetails(Errors.INVALID_INPUT, "Password must be valid"), HttpStatus.BAD_REQUEST);
		}
        Map<String, Object> responseMap = new HashMap<>();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAuthorities("USER");
        user.setUsername(userName);
        UserDetails userDetails = userDetailsService.createUserDetails(userName, user.getPassword());
        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        userRepository.save(user);
        saveUserToken(user, jwtToken);
        responseMap.put("error", false);
        responseMap.put("username", userName);
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", jwtToken);
        return ResponseEntity.ok(responseMap);
    }

    private void revokeAllUserTokens(User user) {
    	var validUserTokens = tokenRepository.findAllValidTokens(user.getId());
    	if (validUserTokens.isEmpty()) {
    		return;
    	}
    	validUserTokens.forEach(t -> {
    		t.setExpired(true);
    		t.setRevoked(true);
    	});
    	tokenRepository.saveAll(validUserTokens);
    }
    
	private void saveUserToken(User user, String jwtToken) {
		Token token = Token.builder()
        		.user(user)
        		.token(jwtToken)
        		.tokenType(TokenType.BEARER)
        		.revoked(false)
        		.expired(false)
        		.build();
        tokenRepository.save(token);
	}
    
    

	private @NotBlank @Size(max = 50) String generateUserName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		long count=1;
		while(userRepository.findUserByUsername(firstName+lastName+count) !=null) {
			count++;
		}
		return firstName+lastName+count;
	}
}
