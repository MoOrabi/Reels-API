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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.exception.Errors;
import com.moorabi.reelsapi.model.FacebookAuthModel;
import com.moorabi.reelsapi.model.FacebookUserModel;
import com.moorabi.reelsapi.model.LoginMethodEnum;
import com.moorabi.reelsapi.model.LoginResponse;
import com.moorabi.reelsapi.model.User;
import com.moorabi.reelsapi.model.UserPrincipal;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;
import com.moorabi.reelsapi.validator.PasswordValidator;

import net.bytebuddy.utility.RandomString;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
public class AuthService {

    protected final Log logger = LogFactory.getLog(getClass());
    public static final String FACEBOOK_AUTH_URL = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=%s";

    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    
//    final WebClient webClient;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager,
                                    JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
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
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
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
        String token = jwtTokenUtil.generateToken(userDetails);
        userRepository.save(user);
        responseMap.put("error", false);
        responseMap.put("username", userName);
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }
    
    public ResponseEntity<?> facebook(FacebookAuthModel facebookAuthModel) {
        String templateUrl = String.format(FACEBOOK_AUTH_URL, facebookAuthModel.getAuthToken());
        FacebookUserModel facebookUserModel = WebClient.create().get().uri(templateUrl).retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    throw new ResponseStatusException(clientResponse.statusCode(), "facebook login error");
                })
                .bodyToMono(FacebookUserModel.class)
                .block();

        final Optional<User> userOptional = userRepository.findByEmail(facebookUserModel.getEmail());

        if (userOptional.isEmpty()) {        //we have no user with given email so register them
            final User user = new User(generateUserName(facebookUserModel.getFirstName(),facebookUserModel.getLastName()),facebookUserModel.getEmail(), new RandomString(10).nextString(), LoginMethodEnum.FACEBOOK);
            userRepository.save(user);
            final UserPrincipal userPrincipal = new UserPrincipal(user);
            String jwt = jwtTokenUtil.generateToken(userPrincipal);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/v1/users/{username}")
                    .buildAndExpand(facebookUserModel.getFirstName()).toUri();

            return ResponseEntity.created(location).body(new LoginResponse(jwt));
        } else { // user exists just login
            final User user = userOptional.get();
            if ((user.getLoginMethodEnum() != LoginMethodEnum.FACEBOOK)) { //check if logged in with different logged in method
                return ResponseEntity.badRequest().body("previously logged in with different login method");
            }

            UserPrincipal userPrincipal = new UserPrincipal(user);
            String jwt = jwtTokenUtil.generateToken(userPrincipal);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("error", false);
            responseMap.put("username", user.getUsername());
            responseMap.put("message", "Logged in successfully");
            responseMap.put("token", jwt);
            return ResponseEntity.ok(responseMap);
        }
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
