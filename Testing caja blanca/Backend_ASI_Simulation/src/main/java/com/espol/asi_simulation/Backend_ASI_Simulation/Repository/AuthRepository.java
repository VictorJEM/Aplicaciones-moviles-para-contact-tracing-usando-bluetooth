package com.espol.asi_simulation.Backend_ASI_Simulation.Repository;

import org.springframework.data.repository.CrudRepository;
import com.espol.asi_simulation.Backend_ASI_Simulation.Model.ExposeeAuthData;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<ExposeeAuthData,Long>{

}
