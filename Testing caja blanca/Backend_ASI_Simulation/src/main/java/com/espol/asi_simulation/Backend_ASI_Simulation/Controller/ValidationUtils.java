package com.espol.asi_simulation.Backend_ASI_Simulation.Controller;

//import java.time.Duration;
import java.util.Base64;

public class ValidationUtils{
	
	private int KEY_LENGTH_BYTES;
	
	public ValidationUtils(int keyLengthBytes){
	    this.KEY_LENGTH_BYTES = keyLengthBytes;
	}
	
	public boolean isValidBase64Key(String value) {
	    try {
	      byte[] key = Base64.getDecoder().decode(value);
	      if (key.length != KEY_LENGTH_BYTES) {
	        return false;
	      }
	      return true;
	    } catch (Exception e) {
	      return false;
	    }
	}

}
