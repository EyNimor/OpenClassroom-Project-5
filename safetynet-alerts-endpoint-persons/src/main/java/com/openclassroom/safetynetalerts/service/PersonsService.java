package com.openclassroom.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.dao.dbReader;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonsService {

    private static final Logger logger = LogManager.getLogger("PersonsService");

    @Autowired
    protected PersonsDao pDao;

    public Persons savePerson(Persons personToSave) {
        logger.info("Person - Sauvegarde : " + personToSave.toString());
        boolean saved = false;
        Persons personSaved = new Persons();
        try { 
            personSaved = pDao.save(personToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved = false) {
            return null;
        }
        else {
            return personSaved;
        }
    }

    public List<String> findCityService(String city) {
        logger.info("Person - Trouver Email avec Paramètre Ville : " + city);
        List<Persons> personsList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        personsList = pDao.findByCity(city);
        for(int i=0 ; i <= personsList.size() - 1 ; i++) {
            emailList.add(personsList.get(i).getEmail());
        }
        return emailList;
    }

    public List<Persons> findAllService() {
        logger.info("Person - Récupérer toute la base de donnée");
        return pDao.findAll();
    }

    public Persons updatePerson(Persons newPersonInfo) {
        logger.info("Person - Mise à Jour : " + newPersonInfo.toString());
        boolean updated = false;
        Persons personUpdated = new Persons();
        try {
            newPersonInfo.setId(pDao.findByFirstNameAndLastName(newPersonInfo.getFirstName(), newPersonInfo.getLastName()).getId());
            pDao.delete(pDao.findByFirstNameAndLastName(newPersonInfo.getFirstName(), newPersonInfo.getLastName()));
            personUpdated = pDao.save(newPersonInfo);
            updated = true;
        }
        catch(IllegalArgumentException e) { 
            logger.error("Échec de Mise à Jour", e);
            e.printStackTrace(); 
        }
        if(updated = false) {
            return null;
        }
        else {
            return personUpdated;
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        logger.info("Person - Suppresion : " + firstName + " " + lastName);
        boolean deleted = false;
        Persons personToDelete;
        try {
            personToDelete = pDao.findByFirstNameAndLastName(firstName, lastName);
            pDao.delete(personToDelete);
            deleted = true;
        }
        catch(IllegalArgumentException e) {
            logger.error("Échec de Suppresion", e);
            e.printStackTrace();
        }
        return deleted;
    }

    public void recoverDatabaseFromJSON(String filename) throws Exception {
        logger.info("Récupération du .JSON - " + filename);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("persons");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
                JSONObject personJSON = (JSONObject) jsonArray.get(i);
                Persons personToSave = new Persons((String) personJSON.get("firstName"),
                                                    (String) personJSON.get("lastName"),
                                                    (String) personJSON.get("address"),
                                                    (String) personJSON.get("city"),
                                                    (String) personJSON.get("phone"),
                                                    (String) personJSON.get("email"),
                                                    (String) personJSON.get("zip"));
                savePerson(personToSave);
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    
}
