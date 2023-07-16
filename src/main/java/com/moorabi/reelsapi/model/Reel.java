package com.moorabi.reelsapi.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "reels")
public class Reel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = {CascadeType.DETACH,
			  CascadeType.MERGE,
			  CascadeType.REFRESH})
	@JoinColumn(name="user_id", nullable=false)
	private AppUser appUser;
	
	@NotEmpty
	@Column(name = "country")
	private String country;
	
	@NotEmpty
	@Column(name = "city")
	private String city;
	
	@Column(name="description")
	private String description;
	
	@NotEmpty
	@Column(name = "video")
	@Lob
	private byte[] videoFile;

	@OneToMany(mappedBy = "reel", cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "reel", cascade = CascadeType.ALL)
	private List<Like> likes;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_reel_id")
	private Reel origin;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	public Reel() {

	}
	
	
	
	public Reel(long id, AppUser appUser, @NotEmpty String country, @NotEmpty String city, String description,
			@NotEmpty byte[] videoFile, List<Comment> comments, List<Like> likes) {
		this.id = id;
		this.appUser = appUser;
		this.country = country;
		this.city = city;
		this.description = description;
		this.videoFile = videoFile;
		this.comments = comments;
		this.likes = likes;
		this.createdAt=LocalDateTime.now();
	}

	public Reel(AppUser appUser, @NotEmpty String country, @NotEmpty String city, String description,
			@NotEmpty byte[] videoFile) {
		this.appUser = appUser;
		this.country = country;
		this.city = city;
		this.description = description;
		this.videoFile = videoFile;
		this.createdAt=LocalDateTime.now();
	}

	public Reel(@NotEmpty String country, @NotEmpty String city, String description) {
		this.country = country;
		this.city = city;
		this.description = description;
		this.createdAt=LocalDateTime.now();
	}

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

	@JsonIgnore
	public AppUser getUser() {
		return appUser;
	}
	
	@JsonIgnore
	public String getUserId() {
		return appUser.getId();
	}

	public void setUser(AppUser appUser) {
		this.appUser = appUser;
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

	public byte[] getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(byte[] videoFile) {
		this.videoFile = videoFile;
	}


	@JsonIgnore
	public Reel getOrigin() {
		return origin;
	}

	public long getOriginId() {
		if(getOrigin()==null) {
			return 0;
		}
		return origin.getId();
	}

	public void setOrigin(Reel origin) {
		this.origin = origin;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
