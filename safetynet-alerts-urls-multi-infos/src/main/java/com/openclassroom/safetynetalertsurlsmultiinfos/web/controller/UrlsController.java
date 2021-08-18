package com.openclassroom.safetynetalertsurlsmultiinfos.web.controller;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import com.openclassroom.safetynetalertsurlsmultiinfos.service.UrlsService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlsController {
    
    private static final Logger logger = LogManager.getLogger("UrlsController");

    @Autowired
    protected UrlsService urlsService;

    protected File filename = new File("../Database/data.json");

    public static boolean testInProgress = false;

    @PostConstruct
    protected void initDB() {
        if(testInProgress == false) {
            logger.info("Récupération du .JSON vers la base de donnée");
            try {
                urlsService.recoverDatabaseFromJSON(filename);
            }
            catch(Exception e) {
                logger.error("Échec de la récupération du .JSON", e);
            }
        }
    }

    @GetMapping(value = "/firestation")
    public MappingJacksonValue firestation(@RequestParam(value = "stationNumber") Integer stationNumber) {
        MappingJacksonValue houseCovered = urlsService.personsCoveredByThisStation(stationNumber);
        return houseCovered;
    }

    @GetMapping(value = "/childAlert")
    public MappingJacksonValue childAlert(@RequestParam(value = "address") String address) {
        MappingJacksonValue house = urlsService.childHouseAtThisAddress(address);
        return house;
    }

    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert(@RequestParam(value = "fireStation") Integer stationNumber) {
        List<String> phoneList = urlsService.phoneOfPersonsCoveredByThisStation(stationNumber);
        return phoneList;
    }

    @GetMapping(value = "/fire")
    public MappingJacksonValue fire(@RequestParam(value = "address") String address) {
        MappingJacksonValue fireStationCovering = urlsService.personsAtThisAddressAndStationCoveringThisAddress(address);
        return fireStationCovering;
    }

    @GetMapping(value = "/flood/stations")
    public MappingJacksonValue flood(@RequestParam(value = "stations") List<Integer> stationNumberList) {
        MappingJacksonValue personsCovered = urlsService.personsCoveredByEachStation(stationNumberList);
        return personsCovered;
    }

    @GetMapping(value = "/personInfo")
    public MappingJacksonValue personInfo(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {
        MappingJacksonValue personInformations = urlsService.personsInformations(firstName, lastName);
        return personInformations;
    }
}
