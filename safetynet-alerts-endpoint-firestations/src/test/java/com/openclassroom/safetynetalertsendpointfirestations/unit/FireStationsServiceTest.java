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

import com.openclassroom.safetynetalertsendpointfirestations.service.FireStationsService;
import com.openclassroom.safetynetalertsendpointfirestations.web.controller.FireStationsController;
import com.openclassroom.safetynetalertslibrary.model.FireStations;

@SpringBootTest
public class FireStationsServiceTest extends FireStationsService {

    private static FireStations fireStationsTest1,
                                fireStationsTest2,
                                fireStationsTest3,
                                fireStationsTest4,
                                fireStationsTest5;

    private static List<FireStations> fireStationsList,
                                    returnedFireStationsList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        FireStationsController.testInProgress = true;

        fireStationsList = new ArrayList<>();

        fireStationsTest1 = new FireStations(2, "2 Rue Du Test", 2);
        fireStationsTest2 = new FireStations(3, "3 Rue Du Test", 3);
        fireStationsTest3 = new FireStations(4, "4 Rue Du Test", 4);
        fireStationsTest4 = new FireStations(5, "5 Rue Du Test", 5);
        fireStationsTest5 = new FireStations(6, "6 Rue Du Test", 6);

        fireStationsList.add(fireStationsTest1);
        fireStationsList.add(fireStationsTest2);
        fireStationsList.add(fireStationsTest3);
        fireStationsList.add(fireStationsTest4);
        fireStationsList.add(fireStationsTest5);
    } 

    @BeforeEach
    private void setUpPerTest() throws Exception {
        fsDao.deleteAll();
    }
    
    @Test
    void recoveringDatabaseTest() {
        try {
            this.recoverDatabaseFromJSON(testDatabasePath);
            returnedFireStationsList = fsDao.findAll();
            assertEquals(fireStationsList.toString(), returnedFireStationsList.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

}
