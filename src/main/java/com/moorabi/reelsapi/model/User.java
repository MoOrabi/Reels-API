package com.moorabi.reelsapi.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {
	
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
	private String email;
	
	
	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;

	@Column(name = "authorities")
	private String authorities;
	
	@Enumerated(EnumType.STRING)
    private LoginMethodEnum loginMethodEnum;
	
	@OneToMany (mappedBy = "user" ,cascade = CascadeType.ALL)
	private List<Reel> reels;


	public User() {
		
	}
	

	public User(@NotBlank @Size(max = 20) String username, @NotBlank String emailId,
			@NotBlank @Size(max = 120) String password) {
		this.username = username;
		this.email = emailId;
		this.password = password;
		this.loginMethodEnum=LoginMethodEnum.NATIVE;
	}
	
	public User(@NotBlank @Size(max = 20) String username, @NotBlank String emailId,
			@NotBlank @Size(max = 120) String password,LoginMethodEnum loginMethodEnum) {
		this.username = username;
		this.email = emailId;
		this.password = password;
		this.loginMethodEnum = loginMethodEnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailId) {
		this.email = emailId;
	}


	@Override
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@Override
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

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}


	public LoginMethodEnum getLoginMethodEnum() {
		return loginMethodEnum;
	}


	public void setLoginMethodEnum(LoginMethodEnum loginMethodEnum) {
		this.loginMethodEnum = loginMethodEnum;
	}
	
	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority(authorities));
	  }


	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }
	
}
