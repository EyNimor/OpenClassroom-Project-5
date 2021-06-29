package com.openclassroom.safetynetalertsendpointmedicalrecords.service;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.dao.MedicalRecordsDao;
import com.openclassroom.safetynetalertslibrary.dao.dbReader;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsService {

    private static final Logger logger = LogManager.getLogger("MedicalRecordsService");
    
    @Autowired
    protected MedicalRecordsDao mrDao;

    public MedicalRecords saveMedicalRecords(MedicalRecords medicalRecordsToSave) {
        logger.info("MedicalRecords - Sauvegarde : " + medicalRecordsToSave.toString());
        boolean saved = false;
        MedicalRecords medicalRecordsSaved = new MedicalRecords();
        try { 
            medicalRecordsSaved = mrDao.save(medicalRecordsToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved = false) {
            return null;
        }
        else {
            return medicalRecordsSaved;
        }
    }

    public List<MedicalRecords> findAllService() {
        logger.info("MedicalRecords - Récupérer toute la base de donnée");
        return mrDao.findAll();
    }

    public MedicalRecords updateMedicalRecords(MedicalRecords newMedicalRecordsInfo) {
        logger.info("MedicalRecords - Mise à Jour : " + newMedicalRecordsInfo.toString());
        boolean updated = false;
        MedicalRecords medicalRecordsUpdated = new MedicalRecords();
        try {
            newMedicalRecordsInfo.setId(mrDao.findByFirstNameAndLastName(newMedicalRecordsInfo.getFirstName(), newMedicalRecordsInfo.getLastName()).getId());
            mrDao.delete(mrDao.findByFirstNameAndLastName(newMedicalRecordsInfo.getFirstName(), newMedicalRecordsInfo.getLastName()));
            medicalRecordsUpdated = mrDao.save(newMedicalRecordsInfo);
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
            return medicalRecordsUpdated;
        }
    }

    public boolean deleteMedicalRecords(String firstName, String lastName) {
        logger.info("MedicalRecords - Suppresion : " + firstName + " " + lastName);
        boolean deleted = false;
        MedicalRecords medicalRecordsToDelete;
        try {
            medicalRecordsToDelete = mrDao.findByFirstNameAndLastName(firstName, lastName);
            mrDao.delete(medicalRecordsToDelete);
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
            JSONArray jsonArray = (JSONArray) dbObject.get("medicalrecords");
            for(int i = 0 ; i < jsonArray.size() ; i++) {
                JSONObject medicalRecordsJSON = (JSONObject) jsonArray.get(i);
                List<String> medications = new ArrayList<>();
                List<String> allergies = new ArrayList<>();
                JSONArray medicationsArray = (JSONArray) medicalRecordsJSON.get("medications");
                JSONArray allergiesArray = (JSONArray) medicalRecordsJSON.get("allergies");
                for(int u = 0 ; u < medicationsArray.size() ; u++) {
                    medications.add((String) medicationsArray.get(u));
                }
                for(int u = 0 ; u < allergiesArray.size() ; u++) {
                    allergies.add((String)  allergiesArray.get(u));
                }
                MedicalRecords medicalRecordsToSave = new MedicalRecords((String) medicalRecordsJSON.get("firstName"),
                                                    (String) medicalRecordsJSON.get("lastName"),
                                                    (String) medicalRecordsJSON.get("birthdate"),
                                                    medications,
                                                    allergies);
                saveMedicalRecords(medicalRecordsToSave);
            }
        }
        catch(Exception e) {
            throw e;
        }
    }

}
