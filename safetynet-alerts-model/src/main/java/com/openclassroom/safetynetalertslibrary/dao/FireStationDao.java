package com.openclassroom.safetynetalertslibrary.dao;

import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FireStationDao extends JpaRepository<FireStations, Integer> {

    FireStations findByAddress(String address);
    
}
