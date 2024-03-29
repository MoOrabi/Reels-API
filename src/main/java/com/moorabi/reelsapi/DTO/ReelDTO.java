package com.moorabi.reelsapi.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.AppUser;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ReelDTO {

	private Long Id;
	private AppUser appUser;
	
	
	private String country;
	
	
	private String city;
	
	
	private String description;
	
	
	private byte[] videoFile;

	
	private List<LikeDTO> likes;
	
	private Reel origin;

	private LocalDateTime createdAt;

	public ReelDTO(Long id, AppUser appUser, String country, String city, String description, byte[] videoFile,
			List<LikeDTO> likes, Reel origin, LocalDateTime createdAt) {
		Id = id;
		this.appUser = appUser;
		this.country = country;
		this.city = city;
		this.description = description;
		this.videoFile = videoFile;
		this.likes = likes;
		this.origin = origin;
		this.createdAt = createdAt;
	}
	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	@JsonIgnore
	public AppUser getUser() {
		return appUser;
	}

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

//	@JsonIgnore
	public byte[] getVideoFile() {
		return videoFile;
	}


	public void setVideoFile(byte[] videoFile) {
		this.videoFile = videoFile;
	}


	public List<LikeDTO> getLikes() {
		return likes;
	}


	public void setLikes(List<LikeDTO> likes) {
		this.likes = likes;
	}


	@JsonIgnore
	public Reel getOrigin() {
		return origin;
	}
	
	public long getOriginId() {
		if(getOrigin()!=null)
			return origin.getId();
		else 
			return 0;
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
