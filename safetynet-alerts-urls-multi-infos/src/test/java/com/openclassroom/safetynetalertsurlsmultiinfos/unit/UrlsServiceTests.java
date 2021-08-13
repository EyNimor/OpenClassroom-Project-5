package com.openclassroom.safetynetalertsurlsmultiinfos.unit;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.model.FireStations;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalertsurlsmultiinfos.service.UrlsService;
import com.openclassroom.safetynetalertsurlsmultiinfos.web.controller.UrlsController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlsServiceTests extends UrlsService {

    private static Persons personsTest1,
                            personsTest2,
                            personsTest3,
                            personsTest4,
                            personsTest5;

    private static List<Persons> personsList,
                                returnedPersonsList;

    private static FireStations fireStationsTest1,
                                fireStationsTest2,
                                fireStationsTest3,
                                fireStationsTest4,
                                fireStationsTest5;

    private static List<FireStations> fireStationsList,
                                    returnedFireStationsList;

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
        UrlsController.testInProgress = true;

        personsList = new ArrayList<>();
        fireStationsList = new ArrayList<>();
        medicalRecordsList = new ArrayList<>();

        personsTest1 = new Persons(1, "Jean-Test", "Test", "1 Rue Du Test", "TestCity", "0655199181", "jean.unit.test@tmail.com", "01860");
        personsTest2 = new Persons(2, "Marie-Test", "Test", "1 Rue Du Test", "TestCity", "0626272643", "marie.unit.test@tmail.com", "01860");
        personsTest3 = new Persons(3, "Kevin-Test", "Test", "2 Rue Du Test", "TestCity", "0672855284", "kevin.unit.test@tmail.com", "01860");
        personsTest4 = new Persons(4, "Monika-Test", "Test", "2 Rue Du Test", "TestCity", "0624659300", "monika.unit.test@tmail.com", "01860");
        personsTest5 = new Persons(5, "Sebastien-Test", "Test", "3 Rue Du Test", "TestCity", "0659396107", "sebastien.unit.test@tmail.com", "01860");

        fireStationsTest1 = new FireStations(2, "2 Rue Du Test", 2);
        fireStationsTest2 = new FireStations(3, "3 Rue Du Test", 3);
        fireStationsTest3 = new FireStations(4, "4 Rue Du Test", 4);
        fireStationsTest4 = new FireStations(5, "5 Rue Du Test", 5);
        fireStationsTest5 = new FireStations(6, "6 Rue Du Test", 6);

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

        personsList.add(personsTest1);
        personsList.add(personsTest2);
        personsList.add(personsTest3);
        personsList.add(personsTest4);
        personsList.add(personsTest5);

        fireStationsList.add(fireStationsTest1);
        fireStationsList.add(fireStationsTest2);
        fireStationsList.add(fireStationsTest3);
        fireStationsList.add(fireStationsTest4);
        fireStationsList.add(fireStationsTest5);

        medicalRecordsList.add(medicalRecordsTest1);
        medicalRecordsList.add(medicalRecordsTest2);
        medicalRecordsList.add(medicalRecordsTest3);
        medicalRecordsList.add(medicalRecordsTest4);
        medicalRecordsList.add(medicalRecordsTest5);
    }

    @BeforeEach
    private void setUpPerTest() {
        pDao.deleteAll();
        fsDao.deleteAll();
        mrDao.deleteAll();
    }

    @Test
    void recoveringDatabaseTest() {
        try {
            this.recoverDatabaseFromJSON(testDatabasePath);
            returnedPersonsList = pDao.findAll();
            returnedFireStationsList = fsDao.findAll();
            returnedMedicalRecordsList = mrDao.findAll();
            assertEquals(personsList.toString(), returnedPersonsList.toString());
            assertEquals(fireStationsList.toString(), returnedFireStationsList.toString());
            assertEquals(medicalRecordsList.toString(), returnedMedicalRecordsList.toString());
        } catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void computingAgeWithLaterMonthThatBirthdateMonth() {
        LocalDateTime testDate = LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0);
        Integer age = this.computePersonAge(medicalRecordsTest1.getBirthdate(), testDate);
        Integer expectedAge = 20;
        assertEquals(expectedAge, age);
    }
    
}
