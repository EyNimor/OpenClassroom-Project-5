package com.openclassroom.safetynetalertsendpointfirestations.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FireStationsController;
import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationTest extends FireStationsController {

    @Autowired
    private FireStationDao fsDao;

    private static FireStations fireStationPostTest,
                                fireStationPutTest,
                                fireStationDeleteTest;

    private static FireStations fireStationsTestDB1,
                                fireStationsTestDB2,
                                fireStationsTestDB3,
                                fireStationsTestDB4,
                                fireStationsTestDB5;

    private List<FireStations> entireDB;
    private static List<FireStations> expectedFireStationList;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        testInProgess = true;

        expectedFireStationList = new ArrayList<>();
        
        fireStationPostTest = new FireStations("1 Rue Du Test", 1);
        fireStationPutTest = new FireStations("1 Rue Du Test", 10);
        fireStationDeleteTest = new FireStations("21 Rue Du Test", 2);

        fireStationsTestDB1 = new FireStations(2, "2 Rue Du Test", 2);
        fireStationsTestDB2 = new FireStations(3, "3 Rue Du Test", 3);
        fireStationsTestDB3 = new FireStations(4, "4 Rue Du Test", 4);
        fireStationsTestDB4 = new FireStations(5, "5 Rue Du Test", 5);
        fireStationsTestDB5 = new FireStations(6, "6 Rue Du Test", 6);

        expectedFireStationList.add(fireStationsTestDB1);
        expectedFireStationList.add(fireStationsTestDB2);
        expectedFireStationList.add(fireStationsTestDB3);
        expectedFireStationList.add(fireStationsTestDB4);
        expectedFireStationList.add(fireStationsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgess = true;
        fsDao.deleteAll();
    }

    @Test
    void postRequestTest() {
        this.newFireStation(fireStationPostTest);
        assertEquals(fsDao.findByAddress(fireStationPostTest.getAddress()).toString(), fireStationPostTest.toString());
    }

    @Test
    void putRequestTest() {
        this.newFireStation(fireStationPostTest);
        this.updateFireStation(fireStationPutTest);
        assertEquals(fsDao.findByAddress(fireStationPutTest.getAddress()).toString(), fireStationPutTest.toString());
    }

    @Test
    void deleteRequestTest() {
        this.newFireStation(fireStationDeleteTest);
        this.deleteFireStation(fireStationDeleteTest.getAddress());
        entireDB = fsDao.findAll();
        assertFalse(entireDB.contains(fireStationDeleteTest));
    }

    @Test
    void initDBTest() {
        filename = testDatabasePath;
        testInProgess = false;
        initDB();
        entireDB = fsDao.findAll();
        assertEquals(expectedFireStationList.toString(), entireDB.toString());
    }
    
}
