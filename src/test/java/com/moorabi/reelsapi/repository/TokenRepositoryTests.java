package com.moorabi.reelsapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.Token;
import com.moorabi.reelsapi.model.TokenType;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenRepositoryTests {

	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	AppUser appUser;
	String id;
	
	@BeforeAll
	void setUp(){
		appUser = new AppUser("informatikerm","informatikerm@gmail.com","Password1234");
		AppUser savedAppUser = userRepository.save(appUser);
		id=savedAppUser.getId();
		
	}
	
	
	
	@Test
	void tokenRepository_findAllValidTokens_allReturned() {
		Token token1 = new Token("123456", TokenType.BEARER, false, false, appUser);
		Token token2 = new Token("1234567", TokenType.BEARER, false, false, appUser);
		tokenRepository.save(token1);
		tokenRepository.save(token2);
		List<Token> validTokens = tokenRepository.findAllValidTokens(id);
		assertThat(validTokens.size()).isEqualTo(2);
		for (Token token : validTokens) {
			assertThat(token.getAppUser().getId()).isEqualTo(id);
		}
	}
	
	@Test
	void tokenRepository_findAllValidTokens_OneFound() {
		Token token1 = new Token("123456", TokenType.BEARER, false, false, appUser);
		Token token2 = new Token("1234567", TokenType.BEARER, false, false, null);
		tokenRepository.save(token1);
		tokenRepository.save(token2);
		List<Token> validTokens = tokenRepository.findAllValidTokens(id);
		assertThat(validTokens.size()).isEqualTo(1);
		for (Token token : validTokens) {
			assertThat(token.getAppUser().getId()).isEqualTo(id);
		}
	}
	
	@Test
	void tokenRepository_findAllValidTokens_NoneFound() {
		List<Token> validTokens = tokenRepository.findAllValidTokens(id);
		assertThat(validTokens.size()).isEqualTo(0);
	}
}
