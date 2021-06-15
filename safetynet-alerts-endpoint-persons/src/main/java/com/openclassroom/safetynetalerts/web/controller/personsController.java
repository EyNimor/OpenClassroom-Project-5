package com.openclassroom.safetynetalerts.web.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertslibrary.dao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalerts.service.PersonsService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api")
public class personsController {

    private static final Logger logger = LogManager.getLogger("PersonsController");

    @Autowired
    protected PersonsService ps;

    @Value("${main.databasePath}")
    protected String filename;

    public static boolean testInProgess = false;

    @PostConstruct
    protected void initDB() {
        if(testInProgess == false) {
            logger.info("Récupération du .JSON vers la base de donnée");
            try {
                ps.recoverDatabaseFromJSON(filename, "persons");
            }
            catch(Exception e) {
                logger.error("Échec de la récupération du .JSON", e);
            }
        }
    }

    @GetMapping(value = "/communityEmail")
    public List<String> emailListByCity(@RequestParam(value = "city") String city) {
        logger.info("Requête GET - Liste d'email, Paramètre Ville");
        return ps.findCityService(city);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Object> newPerson(@RequestBody Persons personToSave) {
        logger.info("Requête POST - Paramètre Body Personne à enregistrer");
        try {
            ps.savePerson(personToSave);
        }
        catch(Exception e) {
            logger.error("Échec de sauvegarde", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(testInProgess == false) {
            try {
                dbWriter.writePersonsToJsonFile(filename, ps.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de sauvegarde vers le .JSON", e); 
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/person")
    public ResponseEntity<Void> updatePerson(@RequestBody Persons newPersonInfo) {
        logger.info("Requête PUT - Paramètre Body Personne à mettre à jour");
        Persons personUpdated = ps.updatePerson(newPersonInfo);

        if(testInProgess == false) {
            try {
                dbWriter.writePersonsToJsonFile(filename, ps.findAllService());
            }
            catch(Exception e) {
                logger.error("Échec de mise à jour du .JSON", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personUpdated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/person")
    public ResponseEntity<Void> deletePerson(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName ) {
        logger.info("Requête DELETE - Paramètre Prénom, Paramètre Nom de Famille");
        boolean deleted = ps.deletePerson(firstName, lastName);

        if(testInProgess == false) {
            try {
                dbWriter.writePersonsToJsonFile(filename, ps.findAllService());
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
