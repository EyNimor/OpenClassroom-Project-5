package com.openclassroom.safetynetalertslibrary.dao;

import java.util.List;

import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FireStationDao extends JpaRepository<FireStations, Integer> {

    FireStations findByAddress(String address);
    List<FireStations> findByStation(Integer station);
    
}
