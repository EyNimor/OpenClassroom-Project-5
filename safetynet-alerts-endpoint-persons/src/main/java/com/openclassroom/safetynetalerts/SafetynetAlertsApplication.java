package com.openclassroom.safetynetalerts;

import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = PersonsDao.class)
@EntityScan(basePackageClasses = Persons.class)
public class SafetynetAlertsApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

}
