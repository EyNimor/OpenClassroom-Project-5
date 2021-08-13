package com.openclassroom.safetynetalerts.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalerts.web.controller.personsController;
import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationSaveTest extends personsController {

    @Autowired
    private PersonsDao pDao;

    private static Persons personPostTest,
                            personPutTest,
                            personDeleteTest,
                            returnedPerson;

    private static Persons personsTestDB1,
                            personsTestDB2,
                            personsTestDB3,
                            personsTestDB4,
                            personsTestDB5;

    private static List<Persons> testDBPersonsList;

    private File testDatabasePath = new File("../Database/testData.json");

    @BeforeAll
    private static void setUp() {
        testInProgress = true;

        testDBPersonsList = new ArrayList<>();
        
        personPostTest = new Persons("Jean-Test", "IntegrationTest", "1 Rue Du Test", "TestCity", "0611223344", "email.post.test@tmail.com", "00000");
        personPutTest = new Persons("Jean-Test", "IntegrationTest", "10 Rue Du Test", "New TestCity", "0611223344", "email.put.test@tmail.com", "00000");
        personDeleteTest = new Persons("Jean-Test", "DeleteTest", "21 Rue Du Delete", "DeleteCity", "0699887766", "email.delete.test@tmail.com", "00000");

        personsTestDB1 = new Persons(2, "Jean-Test", "Test", "1 Rue Du Test", "TestCity", "0655199181", "jean.unit.test@tmail.com", "01860");
        personsTestDB2 = new Persons(3, "Marie-Test", "Test", "1 Rue Du Test", "TestCity", "0626272643", "marie.unit.test@tmail.com", "01860");
        personsTestDB3 = new Persons(4, "Kevin-Test", "Test", "2 Rue Du Test", "TestCity", "0672855284", "kevin.unit.test@tmail.com", "01860");
        personsTestDB4 = new Persons(5, "Monika-Test", "Test", "2 Rue Du Test", "TestCity", "0624659300", "monika.unit.test@tmail.com", "01860");
        personsTestDB5 = new Persons(6, "Sebastien-Test", "Test", "3 Rue Du Test", "TestCity", "0659396107", "sebastien.unit.test@tmail.com", "01860");

        testDBPersonsList.add(personsTestDB1);
        testDBPersonsList.add(personsTestDB2);
        testDBPersonsList.add(personsTestDB3);
        testDBPersonsList.add(personsTestDB4);
        testDBPersonsList.add(personsTestDB5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgress = false;
        filename = testDatabasePath;
        pDao.deleteAll();
    }

    @AfterEach
    private void afterEachTest() {
        dbWriter.writePersonsToJsonFile(filename, testDBPersonsList);
    }

    @Test
    void postRequestTest() {
        this.newPerson(personPostTest);
        pDao.deleteAll();
        try {
            ps.recoverDatabaseFromJSON(filename);
            returnedPerson = pDao.findAll().get(0);
            assertEquals(personPostTest.toString(), returnedPerson.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void putRequestTest() {
        this.newPerson(personPostTest);
        this.updatePerson(personPutTest);
        pDao.deleteAll();
        try {
            ps.recoverDatabaseFromJSON(filename);
            returnedPerson = pDao.findAll().get(0);
            assertEquals(personPutTest.toString(), returnedPerson.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

    @Test
    void deleteRequestTest() {
        dbWriter.writePersonsToJsonFile(filename, testDBPersonsList);
        this.newPerson(personDeleteTest);
        this.deletePerson(personDeleteTest.getFirstName(), personDeleteTest.getLastName());
        try {
            ps.recoverDatabaseFromJSON(filename);
            returnedPerson = pDao.findByFirstNameAndLastName(personDeleteTest.getFirstName(), personDeleteTest.getLastName());
            assertNull(returnedPerson);
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }
    
}
