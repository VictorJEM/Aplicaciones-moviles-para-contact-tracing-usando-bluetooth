package com.espol.asi_simulation.Backend_ASI_Simulation.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espol.asi_simulation.Backend_ASI_Simulation.Model.ExposeeAuthData;
import com.espol.asi_simulation.Backend_ASI_Simulation.Repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthRepository repo;
	
	@Override
	public void upsertExposee(ExposeeAuthData exposee) {
		repo.save(exposee);
	};
	 
	@Override
	public List<ExposeeAuthData> getListExposedAuthData(){
		List<ExposeeAuthData> exps = new ArrayList<>();
		repo.findAll().forEach(exps::add);;
		return exps;
	};

}
