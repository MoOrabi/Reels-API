package com.moorabi.reelsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moorabi.reelsapi.model.AppUser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
	private Long Id;
	private AppUser appUser;
	


	public Long getId() {
		return Id;
	}



	@JsonIgnore
	public AppUser getUser() {
		return appUser;
	}

	public String getUserId() {
		return appUser.getId();
	}

	
	
}
