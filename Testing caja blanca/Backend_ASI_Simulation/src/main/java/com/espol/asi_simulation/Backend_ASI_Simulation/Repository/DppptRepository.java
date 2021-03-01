package com.espol.asi_simulation.Backend_ASI_Simulation.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.espol.asi_simulation.Backend_ASI_Simulation.Model.Exposee;

@Repository
public interface DppptRepository extends CrudRepository<Exposee,Long>{

}