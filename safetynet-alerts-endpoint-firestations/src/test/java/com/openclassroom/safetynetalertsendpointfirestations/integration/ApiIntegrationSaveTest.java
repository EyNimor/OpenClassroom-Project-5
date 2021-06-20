package com.openclassroom.safetynetalertsendpointfirestations.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FireStationsController;
import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.dao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationSaveTest extends FireStationsController {

    @Autowired
    private FireStationDao fsDao;

    private static FireStations fireStationPostTest,
                                fireStationPutTest,
                                fireStationDeleteTest,
                                returnedFireStation;

    private static FireStations fireStationsTestDB1,
                                fireStationsTestDB2,
                                fireStationsTestDB3,
                                fireStationsTestDB4,
                                fireStationsTestDB5;

    private static List<FireStations> testDBFireStationsList;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        testInProgess = true;

        testDBFireStationsList = new ArrayList<>();
        
        fireStationPostTest = new FireStations("1 Rue Du Test", 1);
        fireStationPutTest = new FireStations("1 Rue Du Test", 10);
        fireStationDeleteTest = new FireStations("21 Rue Du Test", 2);

        fireStationsTestDB1 = new FireStations(2, "2 Rue Du Test", 2);
        fireStationsTestDB2 = new FireStations(3, "3 Rue Du Test", 3);
        fireStationsTestDB3 = new FireStations(4, "4 Rue Du Test", 4);
        fireStationsTestDB4 = new FireStations(5, "5 Rue Du Test", 5);
        fireStationsTestDB5 = new FireStations(6, "6 Rue Du Test", 6);

        testDBFireStationsList.add(fireStationsTestDB1);
        testDBFireStationsList.add(fireStationsTestDB2);
        testDBFireStationsList.add(fireStationsTestDB3);
        testDBFireStationsList.add(fireStationsTestDB4);
        testDBFireStationsList.add(fireStationsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgess = false;
        filename = testDatabasePath;
        fsDao.deleteAll();
    }

    @AfterEach
    private void afterEachTest() {
        dbWriter.writeFireStationsToJsonFile(filename, testDBFireStationsList);
    }

    @Test
    void postRequestTest() {
        this.newFireStation(fireStationPostTest);
        fsDao.deleteAll();
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFireStation = fsDao.findAll().get(0);
            assertEquals(fireStationPostTest.toString(), returnedFireStation.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void putRequestTest() {
        this.newFireStation(fireStationPostTest);
        this.updateFireStation(fireStationPutTest);
        fsDao.deleteAll();
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFireStation = fsDao.findAll().get(0);
            assertEquals(fireStationPutTest.toString(), returnedFireStation.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void deleteRequestTest() {
        dbWriter.writeFireStationsToJsonFile(filename, testDBFireStationsList);
        this.newFireStation(fireStationDeleteTest);
        this.deleteFireStation(fireStationDeleteTest.getAddress());
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFireStation = fsDao.findByAddress(fireStationDeleteTest.getAddress());
            assertNull(returnedFireStation);
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }
    
}
