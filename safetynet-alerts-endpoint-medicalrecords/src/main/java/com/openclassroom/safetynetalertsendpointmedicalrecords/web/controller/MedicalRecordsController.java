package com.openclassroom.safetynetalertsendpointmedicalrecords.web.controller;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertsendpointmedicalrecords.service.MedicalRecordsService;
import com.openclassroom.safetynetalertslibrary.dao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

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
public class MedicalRecordsController {
    
    private static final Logger logger = LogManager.getLogger("MedicalRecordsController");

    @Autowired
    protected MedicalRecordsService mrService;

    @Value("${main.databasePath}")
    protected String filename;

    public static boolean testInProgess = false;

    @PostConstruct
    protected void initDB() {
        if(testInProgess == false) {
            logger.info("Récupération du .JSON vers la base de donnée");
            try {
                mrService.recoverDatabaseFromJSON(filename);
            }
            catch(Exception e) {
                logger.error("Échec de la récupération du .JSON", e);
            }
        }
    }

    @PostMapping(value = "/medicalRecords")
    public ResponseEntity<Object> newMedicalRecords(@RequestBody MedicalRecords medicalRecordsToSave) {
        logger.info("Requête POST - Paramètre Body MedicalRecords à enregistrer");
        try {
            mrService.saveMedicalRecords(medicalRecordsToSave);
        }
        catch(Exception e) {
            logger.error("Échec de sauvegarde", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(testInProgess == false) {
            try {
                dbWriter.writeMedicalRecordsToJsonFile(filename, mrService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de sauvegarde vers le .JSON", e); 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/medicalRecords")
    public ResponseEntity<Void> updateMedicalRecords(@RequestBody MedicalRecords newMedicalRecordsInfo) {
        logger.info("Requête PUT - Paramètre Body MedicalRecords à mettre à jour");
        MedicalRecords medicalRecordsUpdated = mrService.updateMedicalRecords(newMedicalRecordsInfo);

        if(testInProgess == false) {
            try {
                dbWriter.writeMedicalRecordsToJsonFile(filename, mrService.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de mise à jour du .JSON", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/medicalRecords")
    public ResponseEntity<Void> deleteMedicalRecords(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName ) {
        logger.info("Requête DELETE - Paramètre Prénom, Paramètre Nom de Famille");
        boolean deleted = mrService.deleteMedicalRecords(firstName, lastName);

        if(testInProgess == false) {
            try {
                dbWriter.writeMedicalRecordsToJsonFile(filename, mrService.findAllService());
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
