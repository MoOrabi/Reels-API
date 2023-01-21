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

@Entity
@Table(name="likes")
public class Like {

	public Like() {
		// TODO Auto-generated constructor stub
	}
	public Like(Reel reel, User user) {
		this.reel = reel;
		this.user = user;
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
	private User user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setUser(User user) {
		this.user = user;
	}

	
}
