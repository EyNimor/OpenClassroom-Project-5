package com.openclassroom.safetynetalertsendpointfirestations.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointfirestations.service.FirestationsService;
import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FirestationsController;
import com.openclassroom.safetynetalertslibrary.model.Firestations;

@SpringBootTest
public class FirestationsServiceTest extends FirestationsService {

    private static Firestations firestationsTest1,
                                firestationsTest2,
                                firestationsTest3,
                                firestationsTest4,
                                firestationsTest5;

    private static List<Firestations> firestationsList,
                                    returnedFirestationsList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        FirestationsController.testInProgress = true;

        firestationsList = new ArrayList<>();

        firestationsTest1 = new Firestations(2, "2 Rue Du Test", 2);
        firestationsTest2 = new Firestations(3, "3 Rue Du Test", 3);
        firestationsTest3 = new Firestations(4, "4 Rue Du Test", 4);
        firestationsTest4 = new Firestations(5, "5 Rue Du Test", 5);
        firestationsTest5 = new Firestations(6, "6 Rue Du Test", 6);

        firestationsList.add(firestationsTest1);
        firestationsList.add(firestationsTest2);
        firestationsList.add(firestationsTest3);
        firestationsList.add(firestationsTest4);
        firestationsList.add(firestationsTest5);
    } 

    @BeforeEach
    private void setUpPerTest() throws Exception {
        fsDao.deleteAll();
    }
    
    @Test
    void recoveringDatabaseTest() {
        try {
            this.recoverDatabaseFromJSON(testDatabasePath);
            returnedFirestationsList = fsDao.findAll();
            assertEquals(firestationsList.toString(), returnedFirestationsList.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

}
