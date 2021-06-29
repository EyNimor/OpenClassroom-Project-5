package com.openclassroom.safetynetalertslibrary.dao;

import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MedicalRecordsDao extends JpaRepository<MedicalRecords, Integer> {

    MedicalRecords findByFirstNameAndLastName(String firstName, String lastName);

}
