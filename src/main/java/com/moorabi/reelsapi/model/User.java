package com.moorabi.reelsapi.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "user_name", unique = true)
	private String username;
	
	@Email
	@NotBlank
	@Column(name = "email_id")
	private String emailId;
	
	
	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;

	@Column(name = "authorities")
	private String authorities;
	
	@OneToMany (mappedBy = "user" ,cascade = CascadeType.ALL)
	private List<Reel> reels;


	public User() {
		
	}
	

	public User(@NotBlank @Size(max = 20) String username, @NotBlank String emailId,
			@NotBlank @Size(max = 120) String password) {
		this.username = username;
		this.emailId = emailId;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setReels(List<Reel> reels) {
		this.reels = reels;
	}


	public List<Reel> getReels() {
		return reels;
	}


	public String getAuthorities() {
		return authorities;
	}


	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	
}
