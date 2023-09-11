package com.moorabi.reelsapi.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.moorabi.reelsapi.exception.ErrorDetails;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

	
	
	@InjectMocks
	private AuthService authServiceUnderTest;
	
	@Mock
	private AuthService authService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private TokenService tokenService;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtUserDetailsService userDetailsService;
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
//	@BeforeEach
//	void setUp() {
//		authServiceUnderTest = new AuthService(userRepository, tokenService, authenticationManager, userDetailsService, jwtTokenUtil);
//	}
	
	@Test
	void registerSuccessed() throws ErrorDetails {
		AppUser muser = new AppUser("MoOrabi", "informatikerm@gmail.com", "Nm12345,");
		muser.setFirstName("Mohammed");
		muser.setLastName("Orabi");
		authServiceUnderTest.registerUser(muser);
		ArgumentCaptor<AppUser> userArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
		verify(userRepository).save(userArgumentCaptor.capture());
		AppUser capturedUser = userArgumentCaptor.getValue();
		assertThat(capturedUser).isEqualTo(muser);
	}
	
	@Test
	void registerFailedDuplicateUsername() {
		AppUser muser = new AppUser("MoOrabi", "informatikerm@gmail.com", "Nm12345,");
		muser.setFirstName("Mohammed");
		muser.setLastName("Orabi");
		given(userRepository.findUserByUsername(muser.getUsername()))
			.willReturn(muser);
		assertThatThrownBy(() -> authServiceUnderTest.registerUser(muser))
		.isInstanceOf(ErrorDetails.class)
		.hasMessage("You Already Have an Account Here");
		verify(userRepository, never()).save(any());
	}
	
	@Test
	void registerFailedPasswordInvalid() throws ErrorDetails {
		AppUser muser = new AppUser("MoOrabi", "informatikerm@gmail.com", "Nm12345");
		muser.setFirstName("Mohammed");
		muser.setLastName("Orabi");
		assertThatThrownBy(() -> authServiceUnderTest.registerUser(muser))
		.isInstanceOf(ErrorDetails.class)
		.hasMessage("Password must be valid");
		verify(userRepository, never()).save(any());
	}
	
//	@Test
//	void loginSuccessed() throws ErrorDetails {
//		AppUser muser = new AppUser("MoOrabi", "informatikerm@gmail.com", "Nm12345,");
//		muser.setFirstName("Mohammed");
//		muser.setLastName("Orabi");
//		authServiceUnderTest.registerUser(muser);
//		String username = "MoOrabi";
//		String password = "Nm12345,";
//		
//		authServiceUnderTest.loginUser(username,password);
////		Authentication auth = authenticationManager
////				.authenticate(
////						new UsernamePasswordAuthenticationToken
////						(username, password)
////						);
//		ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor = 
//					ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
//		verify(authenticationManager).authenticate(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
//		
//		Authentication capturedAuth = usernamePasswordAuthenticationTokenArgumentCaptor.getValue();
//		
////		assertThat(capturedAuth).isEqualTo(auth);
////		assertThat(auth.isAuthenticated());
//		verify(userRepository).findUserByUsername(username);
//		AppUser appUser = userRepository.findUserByUsername(username);
//		verify(jwtTokenUtil).revokeAllUserTokens(appUser);
//		verify(jwtTokenUtil).generateToken(appUser);
//        String jwtToken = jwtTokenUtil.generateToken(appUser);
//		verify(tokenService).saveUserToken(appUser, jwtToken);
//	}
}
