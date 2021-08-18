package com.openclassroom.safetynetalertsendpointmedicalrecords;

import com.openclassroom.safetynetalertslibrary.annotations.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.safetynetalertslibrary.dao.MedicalRecordsDao;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = MedicalRecordsDao.class)
@EntityScan(basePackageClasses = MedicalRecords.class)
@ExcludeFromJacocoGeneratedReport
public class SafetynetAlertsEndpointMedicalrecordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsEndpointMedicalrecordsApplication.class, args);
	}

}
