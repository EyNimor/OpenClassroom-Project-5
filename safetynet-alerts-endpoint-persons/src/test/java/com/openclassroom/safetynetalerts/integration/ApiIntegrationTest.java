package com.openclassroom.safetynetalerts.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalerts.web.controller.personsController;
import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiIntegrationTest extends personsController {

    @Autowired
    private PersonsDao pDao;

    private static Persons personPostTest,
                            personPutTest,
                            personDeleteTest;

    private static Persons personsInitDBTest1,
                            personsInitDBTest2,
                            personsInitDBTest3,
                            personsInitDBTest4,
                            personsInitDBTest5;

    private static List<String> returnedEmailList;
    private static List<String> expectedEmailList;
    private List<Persons> entireDB;
    private static List<Persons> expectedPersonsList;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        testInProgess = true;
        
        expectedEmailList = new ArrayList<>();
        expectedPersonsList = new ArrayList<>();
        
        personPostTest = new Persons("Jean-Test", "IntegrationTest", "1 Rue Du Test", "TestCity", "0611223344", "email.post.test@tmail.com", "00000");
        personPutTest = new Persons("Jean-Test", "IntegrationTest", "10 Rue Du Test", "New TestCity", "0611223344", "email.put.test@tmail.com", "00000");
        personDeleteTest = new Persons("Jean-Test", "DeleteTest", "21 Rue Du Delete", "DeleteCity", "0699887766", "email.delete.test@tmail.com", "00000");

        personsInitDBTest1 = new Persons(2, "Jean-Test", "Test", "1 Rue Du Test", "TestCity", "0655199181", "jean.unit.test@tmail.com", "01860");
        personsInitDBTest2 = new Persons(3, "Marie-Test", "Test", "1 Rue Du Test", "TestCity", "0626272643", "marie.unit.test@tmail.com", "01860");
        personsInitDBTest3 = new Persons(4, "Kevin-Test", "Test", "2 Rue Du Test", "TestCity", "0672855284", "kevin.unit.test@tmail.com", "01860");
        personsInitDBTest4 = new Persons(5, "Monika-Test", "Test", "2 Rue Du Test", "TestCity", "0624659300", "monika.unit.test@tmail.com", "01860");
        personsInitDBTest5 = new Persons(6, "Sebastien-Test", "Test", "3 Rue Du Test", "TestCity", "0659396107", "sebastien.unit.test@tmail.com", "01860");

        expectedPersonsList.add(personsInitDBTest1);
        expectedPersonsList.add(personsInitDBTest2);
        expectedPersonsList.add(personsInitDBTest3);
        expectedPersonsList.add(personsInitDBTest4);
        expectedPersonsList.add(personsInitDBTest5);
    }

    @BeforeEach
    private void setUpPerTest() {
        testInProgess = true;
        pDao.deleteAll();
    }

    @Test
    void postRequestTest() {
        this.newPerson(personPostTest);
        assertEquals(pDao.findByFirstNameAndLastName(personPostTest.getFirstName(), personPostTest.getLastName()).toString(), personPostTest.toString());
    }

    @Test
    void putRequestTest() {
        this.newPerson(personPostTest);
        this.updatePerson(personPutTest);
        assertEquals(pDao.findByFirstNameAndLastName(personPutTest.getFirstName(), personPutTest.getLastName()).toString(), personPutTest.toString());
    }

    @Test
    void deleteRequestTest() {
        this.newPerson(personDeleteTest);
        this.deletePerson(personDeleteTest.getFirstName(), personDeleteTest.getLastName());
        entireDB = pDao.findAll();
        assertFalse(entireDB.contains(personDeleteTest));
    }

    @Test
    void getRequestTest() {
        this.newPerson(personPostTest);
        this.newPerson(personDeleteTest);
        expectedEmailList.add(personPostTest.getEmail());
        returnedEmailList = this.emailListByCity("TestCity");
        assertEquals(expectedEmailList, returnedEmailList);
    }

    @Test
    void initDBTest() {
        filename = testDatabasePath;
        testInProgess = false;
        initDB();
        entireDB = pDao.findAll();
        assertEquals(expectedPersonsList.toString(), entireDB.toString());
    }
    
}
