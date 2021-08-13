package com.openclassroom.safetynetalerts.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.openclassroom.safetynetalerts.service.PersonsService;
import com.openclassroom.safetynetalerts.web.controller.personsController;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PersonsServiceTest extends PersonsService {

    private static Persons personsTest1,
                            personsTest2,
                            personsTest3,
                            personsTest4,
                            personsTest5;

    private static List<Persons> personsList,
                                returnedPersonsList;

    @Value("${test.databasePath}")
    private String testDatabasePath;

    @BeforeAll
    private static void setUp() {
        personsController.testInProgress = true;

        personsList = new ArrayList<>();

        personsTest1 = new Persons(1, "Jean-Test", "UnitServiceTest", "1 Rue Du Test", "TestCity", "0655199181", "jean.unit.test@tmail.com", "01860");
        personsTest2 = new Persons(2, "Marie-Test", "UnitServiceTest", "1 Rue Du Test", "TestCity", "0626272643", "marie.unit.test@tmail.com", "01860");
        personsTest3 = new Persons(3, "Kevin-Test", "UnitServiceTest", "2 Rue Du Test", "TestCity", "0672855284", "kevin.unit.test@tmail.com", "01860");
        personsTest4 = new Persons(4, "Monika-Test", "UnitServiceTest", "2 Rue Du Test", "TestCity", "0624659300", "monika.unit.test@tmail.com", "01860");
        personsTest5 = new Persons(5, "Sebastien-Test", "UnitServiceTest", "3 Rue Du Test", "TestCity", "0659396107", "sebastien.unit.test@tmail.com", "01860");

        personsList.add(personsTest1);
        personsList.add(personsTest2);
        personsList.add(personsTest3);
        personsList.add(personsTest4);
        personsList.add(personsTest5);
    } 

    @BeforeEach
    private void setUpPerTest() throws Exception {
        pDao.deleteAll();
    }
    
    @Test
    void recoveringDatabaseTest() {
        try {
            this.recoverDatabaseFromJSON(testDatabasePath);
            returnedPersonsList = pDao.findAll();
            assertEquals(personsList.toString(), returnedPersonsList.toString());
        }
        catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
            e.printStackTrace();
        }
    }

}
