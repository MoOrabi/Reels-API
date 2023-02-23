package com.moorabi.reelsapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moorabi.reelsapi.model.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
	private Long Id;
	private User user;
	


	public Long getId() {
		return Id;
	}



	@JsonIgnore
	public User getUser() {
		return user;
	}

	public long getUserId() {
		return user.getId();
	}

	
	
}
