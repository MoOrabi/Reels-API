package com.moorabi.reelsapi.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Entity
@Table(name="APPUSERS")
public class AppUser implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	
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
	
	
	private boolean status;
	
	@Enumerated(EnumType.STRING)
    private LoginMethodEnum loginMethodEnum;
	
	@OneToMany (mappedBy = "appUser" ,cascade = CascadeType.ALL)
	private List<Reel> reels;

	@OneToMany(mappedBy = "appUser")
	private List<Token> tokens;

	private boolean active;
	@OneToOne
    private Profile userProfile;
    
    @OneToMany(mappedBy = "name")
    private Set<Role> roles;
    
    @ManyToMany(mappedBy = "members")
    private Set<MessageGroup> messageGroups;
	
	public AppUser() {
		
	}
	
	public AppUser(AppUser appUser) {
        this.id = appUser.id;
        this.username = appUser.username;
        this.password = appUser.password;
        this.email = appUser.email;
        this.active = appUser.active;
        this.userProfile = appUser.userProfile;
        this.roles = appUser.roles;  
        this.status=appUser.status;
    }

	public AppUser(@NotBlank @Size(max = 20) String username, @NotBlank String emailId,
			@NotBlank @Size(max = 120) String password) {
		this.username = username;
		this.email = emailId;
		this.password = password;
		this.loginMethodEnum=LoginMethodEnum.NATIVE;
		this.status=true;
	}
	
	public AppUser(@NotBlank @Size(max = 20) String username, @NotBlank String emailId,
			@NotBlank @Size(max = 120) String password,LoginMethodEnum loginMethodEnum) {
		this.username = username;
		this.email = emailId;
		this.password = password;
		this.loginMethodEnum = loginMethodEnum;
		this.status=true;
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
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
		return Collections.emptyList();
	}


	public List<Token> getTokens() {
		return tokens;
	}


	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
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


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Profile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(Profile userProfile) {
		this.userProfile = userProfile;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
