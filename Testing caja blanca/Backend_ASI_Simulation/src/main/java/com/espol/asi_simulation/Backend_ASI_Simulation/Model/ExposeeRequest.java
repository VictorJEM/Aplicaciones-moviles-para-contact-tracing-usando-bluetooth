package com.espol.asi_simulation.Backend_ASI_Simulation.Model;

//import javax.persistence.Entity;

public class ExposeeRequest {
	
	
	private Integer fake = 0;

    private String key;

    private long keyDate;

    private ExposeeAuthData authData;
    
    private String provincia;
	
    private String canton;
	
	private String parroquia;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public ExposeeAuthData getAuthData() {
      return authData;
    }

    public void setAuthData(ExposeeAuthData authData) {
      this.authData = authData;
    }

    public long getKeyDate() {
      return keyDate;
    }

    public void setKeyDate(long keyDate) {
      this.keyDate = keyDate;
    }

    public Integer isFake() {
      return fake;
    }

    public void setIsFake(Integer fake) {
      this.fake = fake;
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
    
    @Override
    public String toString() {
    	return key + " " + keyDate + " " + fake; 
    }

}