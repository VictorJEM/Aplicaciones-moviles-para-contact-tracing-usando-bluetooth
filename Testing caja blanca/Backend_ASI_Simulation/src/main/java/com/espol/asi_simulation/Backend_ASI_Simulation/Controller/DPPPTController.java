package com.espol.asi_simulation.Backend_ASI_Simulation.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import org.apache.commons.codec.binary.Hex;
import com.google.protobuf.ByteString;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.espol.asi_simulation.Backend_ASI_Simulation.Model.*;
import com.espol.asi_simulation.Backend_ASI_Simulation.Model.proto.Exposed;
import com.espol.asi_simulation.Backend_ASI_Simulation.Services.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/v1")
public class DPPPTController {
	
	public static int num = 1;
	
	@Autowired
	private DPPPTDataServiceImpl dataService;
	
	@Autowired
	private AuthServiceImpl authService;
	
	@GetMapping(value="")
	public @ResponseBody String inicio() {
		return "Saludos desde el backend de ASI_Simulation...";
	}
	
	@PostMapping(value="/exposed")
	public @ResponseBody ResponseEntity<String> addExposee(@RequestBody ExposeeRequest exposeeRequest){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(formatter.format(date) + " -> POST /v1/exposed REQUEST #"+num+"...");
		num = num + 1;
		
		List<ExposeeAuthData> authsCodes = authService.getListExposedAuthData();
		String authRequest = exposeeRequest.getAuthData().getValue();
		
		for(ExposeeAuthData code: authsCodes) {
			if(code.getValue().equalsIgnoreCase(authRequest)) {
				Exposee exposee = new Exposee();
				exposee.setKey(exposeeRequest.getKey());
				exposee.setKeyDate(exposeeRequest.getKeyDate());
				exposee.setProvincia(exposeeRequest.getProvincia());
				exposee.setCanton(exposeeRequest.getCanton());
				exposee.setParroquia(exposeeRequest.getParroquia());
				dataService.upsertExposee(exposee);
				return ResponseEntity.ok().build();
			}
		}
		
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body("Código de autorización NO válido");
	}
	
	@GetMapping(value = "/exposed/{batchReleaseTime}", produces = "application/x-protobuf")
	public @ResponseBody ResponseEntity<Exposed.ProtoExposedList> getExposedByBatch(@PathVariable long batchReleaseTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(formatter.format(date) + " -> GET /v1/exposed/"+batchReleaseTime + " REQUEST #"+num+"...");
		num = num + 1;
		
		List<Exposed.ProtoExposee> exposees = new ArrayList<>();
		
		//Extrae la lista de usuarios con COVID positivo de la base de datos
		List<Exposee> exposeeDB = dataService.getListExposedForBatchReleaseTime(batchReleaseTime);
		
		for(Exposee exp: exposeeDB) {
			if(exp.getKeyDate() <= batchReleaseTime) {
				Exposed.ProtoExposee protoExposee = 
						Exposed.ProtoExposee.newBuilder()
			              .setKey(ByteString.copyFrom(Base64.getDecoder().decode(exp.getKey())))
			              .setKeyDate(exp.getKeyDate())
			              .build();
				exposees.add(protoExposee);
			}
		}
		
		Exposed.ProtoExposedList protoExposedList =
				Exposed.ProtoExposedList.newBuilder()
	            .addAllExposed(exposees)
	            .setBatchReleaseTime(batchReleaseTime)
	            .build();
		
		return ResponseEntity.ok()
				.header("X-BATCH-RELEASE-TIME", Long.toString(batchReleaseTime))
				.body(protoExposedList);
	}

}
