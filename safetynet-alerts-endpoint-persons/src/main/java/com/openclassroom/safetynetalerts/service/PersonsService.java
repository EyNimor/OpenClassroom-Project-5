package com.openclassroom.safetynetalerts.service;

import java.util.List;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonsService {

    @Autowired
    private PersonsDao personsDao;

    @PostConstruct
    public void init() { }

    public Persons savePerson(Persons personToSave) {
        boolean saved = false;
        Persons personSaved = new Persons();
        try { 
            personSaved = personsDao.save(personToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { e.printStackTrace(); }
        if(saved = false) {
            return null;
        }
        else {
            return personSaved;
        }
    }

    public List<String> findCityService(String city) {
        return personsDao.findEmailByCity(city);
    }

    public List<Persons> findAllService() {
        return personsDao.findAll();
    }

    public Persons updatePerson(Persons newPersonInfo) {
        boolean updated = false;
        Persons personUpdated = new Persons();
        try {
            personUpdated = personsDao.save(newPersonInfo);
            updated = true;
        }
        catch(IllegalArgumentException e) { e.printStackTrace(); }
        if(updated = false) {
            return null;
        }
        else {
            return personUpdated;
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        boolean deleted = false;
        Persons personToDelete;
        try {
            personToDelete = personsDao.findByFirstNameAndLastName(firstName, lastName);
            personsDao.delete(personToDelete);
            deleted = true;
        }
        catch(IllegalArgumentException e) { e.printStackTrace(); }
        return deleted;
    }
    
}
