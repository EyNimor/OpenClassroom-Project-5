package com.openclassroom.safetynetalertsendpointmedicalrecords.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointmedicalrecords.service.MedicalRecordsService;
import com.openclassroom.safetynetalertsendpointmedicalrecords.web.controller.MedicalRecordsController;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

@SpringBootTest
public class MedicalRecordsServiceTest extends MedicalRecordsService {

    private static MedicalRecords medicalRecordsTest1,
                                    medicalRecordsTest2,
                                    medicalRecordsTest3,
                                    medicalRecordsTest4,
                                    medicalRecordsTest5;

    private static List<String> medications1,
                                medications2,
                                medications3,
                                medications4,
                                medications5,
                                allergies1,
                                allergies2,
                                allergies3,
                                allergies4,
                                allergies5;

    private static List<MedicalRecords> medicalRecordsList,
                                    returnedMedicalRecordsList;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        MedicalRecordsController.testInProgess = true;

        medicalRecordsList = new ArrayList<>();

        medications1 = new ArrayList<>();
        medications1.add("testazine:150mg");
        medications2 = new ArrayList<>();
        medications3 = new ArrayList<>();
        medications3.add("testacyclaze:100mg");
        medications3.add("testacol:50mg");
        medications4 = new ArrayList<>();
        medications4.add("testacyclaze:120mg");
        medications5 = new ArrayList<>();

        allergies1 = new ArrayList<>();
        allergies1.add("lactose");
        allergies2 = new ArrayList<>();
        allergies3 = new ArrayList<>();
        allergies4 = new ArrayList<>();
        allergies4.add("peanut");
        allergies5 = new ArrayList<>();
        allergies5.add("glucole");

        medicalRecordsTest1 = new MedicalRecords(1, "Jean-Test", "Test", "04/15/2000", medications1, allergies1);
        medicalRecordsTest2 = new MedicalRecords(2, "Marie-Test", "Test", "09/02/1999", medications2, allergies2);
        medicalRecordsTest3 = new MedicalRecords(3, "Kevin-Test", "Test", "02/22/2002", medications3, allergies3);
        medicalRecordsTest4 = new MedicalRecords(4, "Monika-Test", "Test", "03/14/2002", medications4, allergies4);
        medicalRecordsTest5 = new MedicalRecords(5, "Sebastien-Test", "Test", "06/25/1998", medications5, allergies5);

        medicalRecordsList.add(medicalRecordsTest1);
        medicalRecordsList.add(medicalRecordsTest2);
        medicalRecordsList.add(medicalRecordsTest3);
        medicalRecordsList.add(medicalRecordsTest4);
        medicalRecordsList.add(medicalRecordsTest5);
    } 

    @BeforeEach
    private void setUpPerTest() throws Exception {
        mrDao.deleteAll();
    }
    
    @Test
    void recoveringDatabaseTest() {
        try {
            this.recoverDatabaseFromJSON(testDatabasePath);
            returnedMedicalRecordsList = mrDao.findAll();
            assertEquals(medicalRecordsList.toString(), returnedMedicalRecordsList.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

}
