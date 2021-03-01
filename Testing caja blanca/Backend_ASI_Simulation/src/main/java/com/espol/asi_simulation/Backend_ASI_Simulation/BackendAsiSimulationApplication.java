package com.espol.asi_simulation.Backend_ASI_Simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@SpringBootApplication

@ComponentScan ({"com.espol.asi_simulation.Backend_ASI_Simulation.Model", 
	"com.espol.asi_simulation.Backend_ASI_Simulation.Repository", 
	"com.espol.asi_simulation.Backend_ASI_Simulation.Services",
	"com.espol.asi_simulation.Backend_ASI_Simulation.Controller"})
public class BackendAsiSimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendAsiSimulationApplication.class, args);
	}
	
	@Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

}
