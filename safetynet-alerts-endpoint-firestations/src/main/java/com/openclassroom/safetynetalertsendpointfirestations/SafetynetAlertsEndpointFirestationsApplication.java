package com.openclassroom.safetynetalertsendpointfirestations;

import com.openclassroom.safetynetalertslibrary.annotations.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.safetynetalertslibrary.dao.FirestationDao;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = FirestationDao.class)
@EntityScan(basePackageClasses = Firestations.class)
@ExcludeFromJacocoGeneratedReport
public class SafetynetAlertsEndpointFirestationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsEndpointFirestationsApplication.class, args);
	}

}
