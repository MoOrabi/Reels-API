package com.moorabi.reelsapi.model;

import lombok.*;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Profile {

	@Id
	private String id;
    private String displayName;
    private String profilePictureUrl;
    private Date birthday;
    @OneToMany
    private Set<Address> addresses;
}