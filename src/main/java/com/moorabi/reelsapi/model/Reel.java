package com.moorabi.reelsapi.model;

import java.io.File;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "reels")
public class Reel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.DETACH,
			  CascadeType.PERSIST,
			  CascadeType.MERGE,
			  CascadeType.REFRESH})
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "city")
	private String city;
	
	@Column(name="description")
	private String description;
	
	@Column(name = "video")
	private File videoFile;

	@OneToMany(mappedBy = "reel", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "reel", cascade = CascadeType.ALL)
	private List<Like> likes;
	
	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return user.getUsername()+" "+user.getFirstName()+" "+user.getLastName();
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public File getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
}
