package com.openclassroom.safetynetalertsendpointfirestations;

import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = FireStationDao.class)
@EntityScan(basePackageClasses = FireStations.class)
public class SafetynetAlertsEndpointFirestationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsEndpointFirestationsApplication.class, args);
	}

}
