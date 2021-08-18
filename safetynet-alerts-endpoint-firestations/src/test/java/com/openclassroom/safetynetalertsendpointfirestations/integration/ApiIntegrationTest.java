package com.openclassroom.safetynetalertsendpointfirestations.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FirestationsController;
import com.openclassroom.safetynetalertslibrary.dao.FirestationDao;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationTest extends FirestationsController {

    @Autowired
    private FirestationDao fsDao;

    private static Firestations firestationPostTest,
                                firestationPutTest,
                                firestationDeleteTest;

    private static Firestations firestationsTestDB1,
                                firestationsTestDB2,
                                firestationsTestDB3,
                                firestationsTestDB4,
                                firestationsTestDB5;

    private List<Firestations> entireDB;
    private static List<Firestations> expectedFirestationList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        testInProgress = true;

        expectedFirestationList = new ArrayList<>();
        
        firestationPostTest = new Firestations("1 Rue Du Test", 1);
        firestationPutTest = new Firestations("1 Rue Du Test", 10);
        firestationDeleteTest = new Firestations("21 Rue Du Test", 2);

        firestationsTestDB1 = new Firestations(2, "2 Rue Du Test", 2);
        firestationsTestDB2 = new Firestations(3, "3 Rue Du Test", 3);
        firestationsTestDB3 = new Firestations(4, "4 Rue Du Test", 4);
        firestationsTestDB4 = new Firestations(5, "5 Rue Du Test", 5);
        firestationsTestDB5 = new Firestations(6, "6 Rue Du Test", 6);

        expectedFirestationList.add(firestationsTestDB1);
        expectedFirestationList.add(firestationsTestDB2);
        expectedFirestationList.add(firestationsTestDB3);
        expectedFirestationList.add(firestationsTestDB4);
        expectedFirestationList.add(firestationsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgress = true;
        fsDao.deleteAll();
    }

    @Test
    void postRequestTest() {
        this.newFirestation(firestationPostTest);
        assertEquals(fsDao.findByAddress(firestationPostTest.getAddress()).toString(), firestationPostTest.toString());
    }

    @Test
    void putRequestTest() {
        this.newFirestation(firestationPostTest);
        this.updateFirestation(firestationPutTest);
        assertEquals(fsDao.findByAddress(firestationPutTest.getAddress()).toString(), firestationPutTest.toString());
    }

    @Test
    void deleteRequestTest() {
        this.newFirestation(firestationDeleteTest);
        this.deleteFirestation(firestationDeleteTest.getAddress());
        entireDB = fsDao.findAll();
        assertFalse(entireDB.contains(firestationDeleteTest));
    }

    @Test
    void initDBTest() {
        filename = testDatabasePath;
        testInProgress = false;
        initDB();
        entireDB = fsDao.findAll();
        assertEquals(expectedFirestationList.toString(), entireDB.toString());
    }
    
}
