package com.openclassroom.safetynetalertsendpointfirestations.service;

import java.io.File;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbReader;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationsService {

    private static final Logger logger = LogManager.getLogger("FireStationsService");

    @Autowired
    protected FireStationDao fsDao;

    public List<FireStations> findAllService() {
        logger.info("FireStation - Récupérer toute la base de donnée");
        return fsDao.findAll();
    }

    public FireStations saveFireStation(FireStations fireStationToSave) {
        logger.info("FireStation - Sauvegarde : " + fireStationToSave.toString());
        boolean saved = false;
        FireStations fireStationSaved = new FireStations();
        try { 
            fireStationSaved = fsDao.save(fireStationToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved == false) {
            return null;
        }
        else {
            return fireStationSaved;
        }
    }

    public FireStations updatePerson(FireStations newFireStationInfo) {
        logger.info("FireStation - Mise à Jour : " + newFireStationInfo.toString());
        boolean updated = false;
        FireStations fireStationUpdated = new FireStations();
        try {
            newFireStationInfo.setId(fsDao.findByAddress(newFireStationInfo.getAddress()).getId());
            fsDao.delete(fsDao.findByAddress(newFireStationInfo.getAddress()));
            fireStationUpdated = fsDao.save(newFireStationInfo);
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
            return fireStationUpdated;
        }
    }

    public boolean deleteFireStation(String address) {
        logger.info("FireStation - Suppresion : " + address);
        boolean deleted = false;
        FireStations fireStationToDelete;
        try {
            fireStationToDelete = fsDao.findByAddress(address);
            fsDao.delete(fireStationToDelete);
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
                JSONObject fireStationJSON = (JSONObject) jsonArray.get(i);
                FireStations fireStationToSave = new FireStations((String) fireStationJSON.get("address"),
                                                                Integer.parseInt(fireStationJSON.get("station").toString()));
                saveFireStation(fireStationToSave);
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    
}
