package com.openclassroom.safetynetalertsendpointfirestations.web.controller;

import java.io.File;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertsendpointfirestations.service.FirestationsService;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/firestation")
public class FirestationsController {
    
    private static final Logger logger = LogManager.getLogger("FirestationsController");

    @Autowired
    protected FirestationsService fsService;

    protected File filename = new File("../Database/data.json");

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

    @PostMapping(value = "/newFirestation")
    public ResponseEntity<Object> newFirestation(@RequestBody Firestations firestationToSave) {
        logger.info("Requête POST - Paramètre Body Firestation à enregistrer");
        try {
            fsService.saveFirestation(firestationToSave);
        }
        catch(Exception e) {
            logger.error("Échec de sauvegarde", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(testInProgress == false) {
            try {
                dbWriter.writeFirestationsToJsonFile(filename, fsService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de sauvegarde vers le .JSON", e); 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping(value = "/updateFirestation")
    public ResponseEntity<Void> updateFirestation(@RequestBody Firestations newFirestationInfo) {
        logger.info("Requête PUT - Paramètre Body Firestation à mettre à jour");
        fsService.updatePerson(newFirestationInfo);

        if(testInProgress == false) {
            try {
                dbWriter.writeFirestationsToJsonFile(filename, fsService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de mise à jour du .JSON", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/deleteFirestation")
    public ResponseEntity<Void> deleteFirestation(@RequestParam(value = "address") String address) {
        logger.info("Requête DELETE - Paramètre Addresse");
        boolean deleted = fsService.deleteFirestation(address);

        if(testInProgress == false) {
            try {
                dbWriter.writeFirestationsToJsonFile(filename, fsService.findAllService());
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
