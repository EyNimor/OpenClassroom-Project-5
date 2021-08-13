package com.openclassroom.safetynetalertsurlsmultiinfos.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.dao.MedicalRecordsDao;
import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.model.FireStations;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.FireStationCovering;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.HouseCovered;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.HouseURL;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.PersonURL;
import com.openclassroom.safetynetalertsurlsmultiinfos.web.controller.UrlsController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJacksonValue;

@SpringBootTest
public class ApiIntegrationTests extends UrlsController {

    @Autowired
    protected PersonsDao pDao;
    @Autowired
    protected FireStationDao fsDao;
    @Autowired
    protected MedicalRecordsDao mrDao;
    
    private static Persons personsTest1,
                            personsTest2,
                            personsTest3,
                            personsTest4,
                            personsTest5;

    private static List<Persons> testPersonDB;

    private static FireStations fireStationsTest1,
                                fireStationsTest2,
                                fireStationsTest3,
                                fireStationsTest4,
                                fireStationsTest5;

    private static List<FireStations> testFireStationDB;

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

    private static List<MedicalRecords> testMedicalRecordsDB;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        testInProgress = true;

        testPersonDB = new ArrayList<>();
        testFireStationDB = new ArrayList<>();
        testMedicalRecordsDB = new ArrayList<>();

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

        testPersonDB.add(personsTest1);
        testPersonDB.add(personsTest2);
        testPersonDB.add(personsTest3);
        testPersonDB.add(personsTest4);
        testPersonDB.add(personsTest5);

        testFireStationDB.add(fireStationsTest1);
        testFireStationDB.add(fireStationsTest2);
        testFireStationDB.add(fireStationsTest3);
        testFireStationDB.add(fireStationsTest4);
        testFireStationDB.add(fireStationsTest5);

        testMedicalRecordsDB.add(medicalRecordsTest1);
        testMedicalRecordsDB.add(medicalRecordsTest2);
        testMedicalRecordsDB.add(medicalRecordsTest3);
        testMedicalRecordsDB.add(medicalRecordsTest4);
        testMedicalRecordsDB.add(medicalRecordsTest5);
    }
    
    @BeforeEach
    private void setUpPerTest() {
        pDao.deleteAll();
        fsDao.deleteAll();
        mrDao.deleteAll();
        try {
        urlsService.recoverDatabaseFromJSON(testDatabasePath);
        } catch(Exception e) {
            
        }
    }

    @Test
    void fireStationURLTest() {
        PersonURL personURL1 = new PersonURL(personsTest3);
        PersonURL personURL2 = new PersonURL(personsTest4);
        List<PersonURL> personURLList = new ArrayList<>();
        personURLList.add(personURL1);
        personURLList.add(personURL2);
        HouseURL house = new HouseURL(personsTest3.getAddress(), personURLList);
        List<HouseURL> houseList = new ArrayList<>();
        houseList.add(house);
        HouseCovered expectedObject = new HouseCovered(houseList, 2, 0);

        MappingJacksonValue returnedObject = this.firestation(2);
        assertEquals(expectedObject.toString(), returnedObject.getValue().toString());
    }

    @Test
    void childAlertTest() {
        MappingJacksonValue returnedObject = this.childAlert(personsTest1.getAddress());
        assertNull(returnedObject);
    }

    @Test
    void phoneAlertTest() {
        List<String> expectedObject = new ArrayList<>();
        expectedObject.add(personsTest3.getPhone());
        expectedObject.add(personsTest4.getPhone());

        List<String> returnedObject = this.phoneAlert(2);
        assertEquals(expectedObject.toString(), returnedObject.toString());
    }

    @Test
    void fireTest() {
        PersonURL personURL = new PersonURL(personsTest5, medicalRecordsTest5);
        List<PersonURL> personURLList = new ArrayList<>();
        personURLList.add(personURL);
        FireStationCovering expectedObject = new FireStationCovering(personURLList, fireStationsTest2.getStation());

        MappingJacksonValue returnedObject = this.fire(personsTest5.getAddress());
        assertEquals(expectedObject.toString(), returnedObject.getValue().toString());
    }

    @Test
    void floodTest() {
        List<Integer> stationNumberList = new ArrayList<>();
        stationNumberList.add(fireStationsTest1.getStation());
        stationNumberList.add(fireStationsTest2.getStation());
        PersonURL personURL1 = new PersonURL(personsTest3, medicalRecordsTest3);
        PersonURL personURL2 = new PersonURL(personsTest4, medicalRecordsTest4);
        PersonURL personURL3 = new PersonURL(personsTest5, medicalRecordsTest5);
        List<PersonURL> personList1 = new ArrayList<>();
        personList1.add(personURL1);
        personList1.add(personURL2);
        List<PersonURL> personList2 = new ArrayList<>();
        personList2.add(personURL3);
        HouseURL house1 = new HouseURL(personsTest3.getAddress(), personList1);
        HouseURL house2 = new HouseURL(personsTest5.getAddress(), personList2);
        List<HouseURL> expectedObject = new ArrayList<>();
        expectedObject.add(house1);
        expectedObject.add(house2);

        MappingJacksonValue returnedObject = this.flood(stationNumberList);
        assertEquals(expectedObject.toString(), returnedObject.getValue().toString());
    }

    @Test
    void personInfoTest() {
        PersonURL personURL = new PersonURL(personsTest1, medicalRecordsTest1);
        List<PersonURL> persons = new ArrayList<>();
        persons.add(personURL);
        HouseURL expectedObject = new HouseURL(personsTest1.getAddress(), persons);
        
        MappingJacksonValue returnedObject = this.personInfo(personsTest1.getFirstName(), personsTest1.getLastName());
        assertEquals(expectedObject.toString(), returnedObject.getValue().toString());
    }
    
}
