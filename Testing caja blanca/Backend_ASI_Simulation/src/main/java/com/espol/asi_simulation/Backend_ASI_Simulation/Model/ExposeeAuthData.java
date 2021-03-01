package com.espol.asi_simulation.Backend_ASI_Simulation.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "auth_codes")
public class ExposeeAuthData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String value;

	public String getValue() {
	  return value;
	}
	
	public void setValue(String value) {
	  this.value = value;
	}

}
