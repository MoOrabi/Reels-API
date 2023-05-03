package com.moorabi.reelsapi.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

	@Id
	private String name;

	public final static Role USER = new Role("USER");
    public final static Role FACEBOOK_USER = new Role("FACEBOOK_USER");

    
    
}
