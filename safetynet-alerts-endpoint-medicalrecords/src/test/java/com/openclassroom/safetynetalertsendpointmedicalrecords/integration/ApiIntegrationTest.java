package com.openclassroom.safetynetalertsendpointmedicalrecords.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertsendpointmedicalrecords.web.controller.MedicalRecordsController;
import com.openclassroom.safetynetalertslibrary.dao.MedicalRecordsDao;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationTest extends MedicalRecordsController {

    @Autowired
    private MedicalRecordsDao mrDao;

    private static MedicalRecords medicalRecordsPostTest,
                                    medicalRecordsPutTest,
                                    medicalRecordsDeleteTest;

    private static MedicalRecords medicalRecordsTestDB1,
                                    medicalRecordsTestDB2,
                                    medicalRecordsTestDB3,
                                    medicalRecordsTestDB4,
                                    medicalRecordsTestDB5;

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

    private List<MedicalRecords> entireDB;
    private static List<MedicalRecords> expectedMedicalRecordsList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        testInProgress = true;

        expectedMedicalRecordsList = new ArrayList<>();

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
        
        medicalRecordsPostTest = new MedicalRecords("Jean-Test", "IntegrationTest", "01/01/2000", medications2, allergies2);
        medicalRecordsPutTest = new MedicalRecords("Jean-Test", "IntegrationTest", "06/15/2000", medications1, allergies1);
        medicalRecordsDeleteTest = new MedicalRecords("Jean-Test", "DeleteTest", "12/31/2000", medications2, allergies2);

        medicalRecordsTestDB1 = new MedicalRecords(2, "Jean-Test", "Test", "04/15/2000", medications1, allergies1);
        medicalRecordsTestDB2 = new MedicalRecords(3, "Marie-Test", "Test", "09/02/1999", medications2, allergies2);
        medicalRecordsTestDB3 = new MedicalRecords(4, "Kevin-Test", "Test", "02/22/2002", medications3, allergies3);
        medicalRecordsTestDB4 = new MedicalRecords(5, "Monika-Test", "Test", "03/14/2002", medications4, allergies4);
        medicalRecordsTestDB5 = new MedicalRecords(6, "Sebastien-Test", "Test", "06/25/1998", medications5, allergies5);

        expectedMedicalRecordsList.add(medicalRecordsTestDB1);
        expectedMedicalRecordsList.add(medicalRecordsTestDB2);
        expectedMedicalRecordsList.add(medicalRecordsTestDB3);
        expectedMedicalRecordsList.add(medicalRecordsTestDB4);
        expectedMedicalRecordsList.add(medicalRecordsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgress = true;
        mrDao.deleteAll();
    }

    @Test
    void postRequestTest() {
        this.newMedicalRecords(medicalRecordsPostTest);
        assertEquals(mrDao.findByFirstNameAndLastName(medicalRecordsPostTest.getFirstName(), medicalRecordsPostTest.getLastName()).toString(), medicalRecordsPostTest.toString());
    }

    @Test
    void putRequestTest() {
        this.newMedicalRecords(medicalRecordsPostTest);
        this.updateMedicalRecords(medicalRecordsPutTest);
        assertEquals(mrDao.findByFirstNameAndLastName(medicalRecordsPutTest.getFirstName(), medicalRecordsPutTest.getLastName()).toString(), medicalRecordsPutTest.toString());
    }

    @Test
    void deleteRequestTest() {
        this.newMedicalRecords(medicalRecordsDeleteTest);
        this.deleteMedicalRecords(medicalRecordsDeleteTest.getFirstName(), medicalRecordsDeleteTest.getLastName());
        entireDB = mrDao.findAll();
        assertFalse(entireDB.contains(medicalRecordsDeleteTest));
    }

    @Test
    void initDBTest() {
        filename = testDatabasePath;
        testInProgress = false;
        initDB();
        entireDB = mrDao.findAll();
        assertEquals(expectedMedicalRecordsList.toString(), entireDB.toString());
    }
    
}
