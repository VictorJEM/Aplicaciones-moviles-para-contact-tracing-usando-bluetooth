package com.espol.asi_simulation.Backend_ASI_Simulation.Services;

import java.util.List;
import com.espol.asi_simulation.Backend_ASI_Simulation.Model.ExposeeAuthData;

public interface AuthService {
	
	 void upsertExposee(ExposeeAuthData exposee);
	 
	 List<ExposeeAuthData> getListExposedAuthData();

}