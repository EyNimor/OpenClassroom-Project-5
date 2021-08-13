package com.openclassroom.safetynetalertsurlsmultiinfos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.openclassroom.safetynetalertslibrary.dao")
@EntityScan(basePackages =  "com.openclassroom.safetynetalertslibrary.model")
public class SafetynetAlertsUrlsMultiInfosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsUrlsMultiInfosApplication.class, args);
	}

}
