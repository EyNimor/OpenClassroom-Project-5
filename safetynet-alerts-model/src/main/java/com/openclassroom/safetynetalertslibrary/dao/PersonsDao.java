package com.openclassroom.safetynetalertslibrary.dao;

import java.util.List;

import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface PersonsDao extends JpaRepository<Persons, Integer> {
    
    Persons findByFirstNameAndLastName(String firstName, String lastName);
    List<String> findEmailByCity(String city);

}
