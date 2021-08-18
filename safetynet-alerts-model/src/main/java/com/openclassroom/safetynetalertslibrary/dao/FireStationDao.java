package com.openclassroom.safetynetalertslibrary.dao;

import java.util.List;

import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FirestationDao extends JpaRepository<Firestations, Integer> {

    Firestations findByAddress(String address);
    List<Firestations> findByStation(Integer station);
    
}
