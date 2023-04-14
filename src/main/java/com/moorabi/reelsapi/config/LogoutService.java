package com.moorabi.reelsapi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.moorabi.reelsapi.repository.TokenRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;
	
	@Override
	public void logout(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) {
		final String requestTokenHeader = request.getHeader("Authorization");
        final String jwtToken;
		if (requestTokenHeader==null||!requestTokenHeader.startsWith("Bearer ")) {
        	
			return;
        }         
        
        jwtToken = requestTokenHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwtToken)
        		.orElse(null);
        if(storedToken!=null) {
        	storedToken.setExpired(true);
        	storedToken.setRevoked(true);
        	tokenRepository.save(storedToken);
        }
	}

}
