package com.moorabi.reelsapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Address {

	@Id
    private String id;
    private String country;
    private String city;
    private String zipCode;
    private String streetName;
    private int buildingNumber;
}
