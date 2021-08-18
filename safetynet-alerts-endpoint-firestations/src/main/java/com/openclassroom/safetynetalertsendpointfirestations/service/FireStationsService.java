package com.openclassroom.safetynetalertsendpointfirestations.service;

import java.io.File;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.dao.FirestationDao;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbReader;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationsService {

    private static final Logger logger = LogManager.getLogger("FirestationsService");

    @Autowired
    protected FirestationDao fsDao;

    public List<Firestations> findAllService() {
        logger.info("Firestation - Récupérer toute la base de donnée");
        return fsDao.findAll();
    }

    public Firestations saveFirestation(Firestations firestationToSave) {
        logger.info("Firestation - Sauvegarde : " + firestationToSave.toString());
        boolean saved = false;
        Firestations firestationSaved = new Firestations();
        try { 
            firestationSaved = fsDao.save(firestationToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved == false) {
            return null;
        }
        else {
            return firestationSaved;
        }
    }

    public Firestations updatePerson(Firestations newFirestationInfo) {
        logger.info("Firestation - Mise à Jour : " + newFirestationInfo.toString());
        boolean updated = false;
        Firestations firestationUpdated = new Firestations();
        try {
            newFirestationInfo.setId(fsDao.findByAddress(newFirestationInfo.getAddress()).getId());
            fsDao.delete(fsDao.findByAddress(newFirestationInfo.getAddress()));
            firestationUpdated = fsDao.save(newFirestationInfo);
            updated = true;
        }
        catch(IllegalArgumentException e) { 
            logger.error("Échec de Mise à Jour", e);
            e.printStackTrace(); 
        }
        if(updated == false) {
            return null;
        }
        else {
            return firestationUpdated;
        }
    }

    public boolean deleteFirestation(String address) {
        logger.info("Firestation - Suppresion : " + address);
        boolean deleted = false;
        Firestations firestationToDelete;
        try {
            firestationToDelete = fsDao.findByAddress(address);
            fsDao.delete(firestationToDelete);
            deleted = true;
        }
        catch(IllegalArgumentException e) {
            logger.error("Échec de Suppresion", e);
            e.printStackTrace();
        }
        return deleted;
    }

    public void recoverDatabaseFromJSON(File filename) throws Exception {
        logger.info("Récupération du .JSON - " + filename);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("firestations");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
                JSONObject firestationJSON = (JSONObject) jsonArray.get(i);
                Firestations firestationToSave = new Firestations((String) firestationJSON.get("address"),
                                                                Integer.parseInt(firestationJSON.get("station").toString()));
                saveFirestation(firestationToSave);
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    
}
