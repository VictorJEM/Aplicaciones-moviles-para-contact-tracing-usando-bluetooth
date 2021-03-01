package com.espol.asi_simulation.Backend_ASI_Simulation.Services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.espol.asi_simulation.Backend_ASI_Simulation.Model.Exposee;
import com.espol.asi_simulation.Backend_ASI_Simulation.Repository.*;


@Service
public class DPPPTDataServiceImpl implements DPPPTDataService{
	
	@Autowired
    private DppptRepository repository;
    
	@Override
	public void upsertExposee(Exposee exposee) {
		repository.save(exposee);
	}
	
	@Override
	public List<Exposee> getListExposedForBatchReleaseTime(long batchReleaseTime){
		List<Exposee> exps = new ArrayList<>();
		repository.findAll().forEach(exps::add);;
		return exps;
	}
	
}
