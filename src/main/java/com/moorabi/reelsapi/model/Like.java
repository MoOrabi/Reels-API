package com.moorabi.reelsapi.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="likes")
public class Like {

	public Like() {
		// TODO Auto-generated constructor stub
	}
	public Like(Reel reel, AppUser appUser) {
		this.reel = reel;
		this.appUser = appUser;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@ManyToOne 
	@JoinColumn(name= "reel_liked")
	private Reel reel;
	
	@ManyToOne 
	@JoinColumn(name= "user_likes")
	private AppUser appUser;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@JsonIgnore
	public Reel getReel() {
		return reel;
	}

	public Long getReelId() {
		return reel.getId();
	}
	
	public void setReel(Reel reel) {
		this.reel = reel;
	}

	@JsonIgnore
	public AppUser getUser() {
		return appUser;
	}

	public String getUserName() {
		return appUser.getUsername();
	}
	
	public void setUser(AppUser appUser) {
		this.appUser = appUser;
	}

	
}
