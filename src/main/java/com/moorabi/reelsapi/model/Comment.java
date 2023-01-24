package com.moorabi.reelsapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="comments")
public class Comment {

	public Comment() {
		// TODO Auto-generated constructor stub
	}
	public Comment(String description, Reel reel, User user) {
		super();
		this.description = description;
		this.reel = reel;
		this.user = user;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="description")
	private String description;
	
//	@JsonManagedReference
	@ManyToOne 
	@JoinColumn(name= "reel_commented")
	private Reel reel;
	
//	@JsonManagedReference
	@ManyToOne 
	@JoinColumn(name= "user_comments")
	private User user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getReel() {
		return reel.getId();
	}

	public void setReel(Reel reel) {
		this.reel = reel;
	}

	public long getUser() {
		return user.getId();
	}
	
//	public User getUserA() {
//		return user;
//	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
