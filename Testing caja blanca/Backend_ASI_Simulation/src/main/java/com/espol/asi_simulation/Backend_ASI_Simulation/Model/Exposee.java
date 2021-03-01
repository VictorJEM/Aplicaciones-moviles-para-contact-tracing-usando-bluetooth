package com.espol.asi_simulation.Backend_ASI_Simulation.Model;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exposees")
public class Exposee {
  //@JsonIgnore private Integer Id;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer Id;

  private String key;

  private long keyDate;
  
  private String provincia;
  
  private String canton;
  
  private String parroquia;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @JsonIgnore
  public Integer getId() {
    return Id;
  }

  public void setId(Integer id) {
    Id = id;
  }

  public long getKeyDate() {
    return keyDate;
  }

  public void setKeyDate(long keyDate) {
    this.keyDate = keyDate;
  }
  
  public String getProvincia() {
	  return provincia;
  }
  
  public void setProvincia(String provincia) {
	  this.provincia = provincia;
  }
  
  public String getCanton() {
	  return canton;
  }
  
  public void setCanton(String canton) {
	  this.canton = canton;
  }
  
  public String getParroquia() {
	  return parroquia;
  }
  
  public void setParroquia(String parroquia) {
	  this.parroquia = parroquia;
  }
  
}

