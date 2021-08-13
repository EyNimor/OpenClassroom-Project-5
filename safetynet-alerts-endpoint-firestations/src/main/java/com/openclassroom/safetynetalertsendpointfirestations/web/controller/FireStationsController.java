package com.openclassroom.safetynetalertsendpointfirestations.web.controller;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertsendpointfirestations.service.FireStationsService;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class FireStationsController {
    
    private static final Logger logger = LogManager.getLogger("FireStationsController");

    @Autowired
    protected FireStationsService fsService;

    @Value("${main.databasePath}")
    protected String filename;

    public static boolean testInProgress = false;

    @PostConstruct
    protected void initDB() {
        if(testInProgress == false) {
            logger.info("Récupération du .JSON vers la base de donnée");
            try {
                fsService.recoverDatabaseFromJSON(filename);
            }
            catch(Exception e) {
                logger.error("Échec de la récupération du .JSON", e);
            }
        }
    }

    @PostMapping(value = "/fireStation")
    public ResponseEntity<Object> newFireStation(@RequestBody FireStations fireStationToSave) {
        logger.info("Requête POST - Paramètre Body FireStation à enregistrer");
        try {
            fsService.saveFireStation(fireStationToSave);
        }
        catch(Exception e) {
            logger.error("Échec de sauvegarde", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(testInProgress == false) {
            try {
                dbWriter.writeFireStationsToJsonFile(filename, fsService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de sauvegarde vers le .JSON", e); 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping(value = "/fireStation")
    public ResponseEntity<Void> updateFireStation(@RequestBody FireStations newFireStationInfo) {
        logger.info("Requête PUT - Paramètre Body FireStation à mettre à jour");
        FireStations fireStationUpdated = fsService.updatePerson(newFireStationInfo);

        if(testInProgress == false) {
            try {
                dbWriter.writeFireStationsToJsonFile(filename, fsService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de mise à jour du .JSON", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/fireStation")
    public ResponseEntity<Void> deleteFireStation(@RequestParam(value = "address") String address) {
        logger.info("Requête DELETE - Paramètre Addresse");
        boolean deleted = fsService.deleteFireStation(address);

        if(testInProgress == false) {
            try {
                dbWriter.writeFireStationsToJsonFile(filename, fsService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de mise à jour du .JSON", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        if (deleted == false)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().build();
    }

}
