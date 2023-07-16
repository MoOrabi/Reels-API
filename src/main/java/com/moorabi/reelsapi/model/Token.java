package com.moorabi.reelsapi.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

	// This Constructor I am using just for Testing purposes so far
	public Token(String token, TokenType tokenType, boolean expired, boolean revoked, AppUser appUser){
		this.token = token;
		this.tokenType = tokenType;
		this.expired = expired;
		this.revoked = revoked;
		this.appUser = appUser;
	}
	
	@Id
	@GeneratedValue
	private long id;
	
	private String token;
	
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	private boolean expired;
	
	private boolean revoked;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser appUser;
	
}
