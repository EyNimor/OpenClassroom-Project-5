package com.openclassroom.safetynetalertsendpointfirestations.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FirestationsController;
import com.openclassroom.safetynetalertslibrary.dao.FirestationDao;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationSaveTest extends FirestationsController {

    @Autowired
    private FirestationDao fsDao;

    private static Firestations firestationPostTest,
                                firestationPutTest,
                                firestationDeleteTest,
                                returnedFirestation;

    private static Firestations firestationsTestDB1,
                                firestationsTestDB2,
                                firestationsTestDB3,
                                firestationsTestDB4,
                                firestationsTestDB5;

    private static List<Firestations> testDBFirestationsList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        testInProgress = true;

        testDBFirestationsList = new ArrayList<>();
        
        firestationPostTest = new Firestations("1 Rue Du Test", 1);
        firestationPutTest = new Firestations("1 Rue Du Test", 10);
        firestationDeleteTest = new Firestations("21 Rue Du Test", 2);

        firestationsTestDB1 = new Firestations(2, "2 Rue Du Test", 2);
        firestationsTestDB2 = new Firestations(3, "3 Rue Du Test", 3);
        firestationsTestDB3 = new Firestations(4, "4 Rue Du Test", 4);
        firestationsTestDB4 = new Firestations(5, "5 Rue Du Test", 5);
        firestationsTestDB5 = new Firestations(6, "6 Rue Du Test", 6);

        testDBFirestationsList.add(firestationsTestDB1);
        testDBFirestationsList.add(firestationsTestDB2);
        testDBFirestationsList.add(firestationsTestDB3);
        testDBFirestationsList.add(firestationsTestDB4);
        testDBFirestationsList.add(firestationsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgress = false;
        filename = testDatabasePath;
        fsDao.deleteAll();
    }

    @AfterEach
    private void afterEachTest() {
        dbWriter.writeFirestationsToJsonFile(filename, testDBFirestationsList);
    }

    @Test
    void postRequestTest() {
        this.newFirestation(firestationPostTest);
        fsDao.deleteAll();
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFirestation = fsDao.findAll().get(0);
            assertEquals(firestationPostTest.toString(), returnedFirestation.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void putRequestTest() {
        this.newFirestation(firestationPostTest);
        this.updateFirestation(firestationPutTest);
        fsDao.deleteAll();
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFirestation = fsDao.findAll().get(0);
            assertEquals(firestationPutTest.toString(), returnedFirestation.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void deleteRequestTest() {
        dbWriter.writeFirestationsToJsonFile(filename, testDBFirestationsList);
        this.newFirestation(firestationDeleteTest);
        this.deleteFirestation(firestationDeleteTest.getAddress());
        try {
            fsService.recoverDatabaseFromJSON(filename);
            returnedFirestation = fsDao.findByAddress(firestationDeleteTest.getAddress());
            assertNull(returnedFirestation);
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }
    
}
