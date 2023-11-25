package com.moorabi.reelsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moorabi.reelsapi.model.Token;
import com.moorabi.reelsapi.model.TokenType;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.TokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	@Transactional
	public void saveUserToken(AppUser appUser, String jwtToken) {
		tokenRepository.deleteAllInvalidTokens();
		Token token = Token.builder()
        		.appUser(appUser)
        		.token(jwtToken)
        		.tokenType(TokenType.BEARER)
        		.revoked(false)
        		.expired(false)
        		.build();
        tokenRepository.save(token);
	}
}
