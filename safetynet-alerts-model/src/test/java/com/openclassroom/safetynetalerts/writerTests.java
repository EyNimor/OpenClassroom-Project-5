package com.openclassroom.safetynetalerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.jsonDao.dbReader;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbWriter;
import com.openclassroom.safetynetalertslibrary.model.Firestations;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = dbWriter.class)
public class writerTests extends dbWriter {

    File filename = new File("src/test/resources/writerTestFile.json");

    private static Persons person;
    private static List<Persons> personsList;

    private static Firestations firestation;
    private static List<Firestations> firestationsList;

    private static List<String> medications;
    private static List<String> allergies;
    private static MedicalRecords medicalRecords;
    private static List<MedicalRecords> medicalRecordsList;

    private static Persons personWrited;
    private static Firestations firestationWrited;
    private static MedicalRecords medicalRecordsWrited;
    
    @BeforeAll
    static void setUp() {
        person = new Persons("Jean", "Terre", "5 Rue Sainte-Croix-de-la-Bretonnerie", "Paris", "0645870666", "je-fais-des-tests-decriture@tests.fr", "75104");
        personsList = new ArrayList<>();
        personsList.add(person);

        firestation = new Firestations("Rue Sainte-Croix-de-la-Bretonnerie", 1);
        firestationsList = new ArrayList<>();
        firestationsList.add(firestation);

        medications = new ArrayList<>();
        medications.add("zirtecset:10mg");
        allergies = new ArrayList<>();
        allergies.add("lactose");
        medicalRecords = new MedicalRecords("Jean", "Terre", "25/06/2002", medications, allergies);
        medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(medicalRecords);
    }

    @Test
    void personWriterTest() {
        writePersonsToJsonFile(filename, personsList);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("persons");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
            JSONObject personJSON = (JSONObject) jsonArray.get(i);
            personWrited = new Persons((String) personJSON.get("firstName"),
                                                (String) personJSON.get("lastName"),
                                                (String) personJSON.get("address"),
                                                (String) personJSON.get("city"),
                                                (String) personJSON.get("phone"),
                                                (String) personJSON.get("email"),
                                                (String) personJSON.get("zip"));
            }
        } catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
			e.printStackTrace();
        }
        assertEquals(person.toString(), personWrited.toString());
    }

    @Test
    void firestationWriterTest() {
        writeFirestationsToJsonFile(filename, firestationsList);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("firestations");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
            JSONObject firestationJSON = (JSONObject) jsonArray.get(i);
            firestationWrited = new Firestations((String) firestationJSON.get("address"),
                                                    Integer.parseInt(firestationJSON.get("station").toString()));
            }
        } catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
			e.printStackTrace();
        }
        assertEquals(firestation.toString(), firestationWrited.toString());
    }

    @Test
    void medicalRecordsWriterTest() {
        writeMedicalRecordsToJsonFile(filename, medicalRecordsList);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("medicalrecords");
            for(int i = 0 ; i < jsonArray.size() ; i++) {
                JSONObject medicalRecordsJSON = (JSONObject) jsonArray.get(i);
                List<String> medications = new ArrayList<>();
                List<String> allergies = new ArrayList<>();
                JSONArray medicationsArray = (JSONArray) medicalRecordsJSON.get("medications");
                JSONArray allergiesArray = (JSONArray) medicalRecordsJSON.get("allergies");
                for(int u = 0 ; u < medicationsArray.size() ; u++) {
                    medications.add((String) medicationsArray.get(u));
                }
                for(int u = 0 ; u < allergiesArray.size() ; u++) {
                    allergies.add((String)  allergiesArray.get(u));
                }
                medicalRecordsWrited = new MedicalRecords((String) medicalRecordsJSON.get("firstName"),
                                                        (String) medicalRecordsJSON.get("lastName"),
                                                        (String) medicalRecordsJSON.get("birthdate"),
                                                        medications,
                                                        allergies);
            }
        } catch(Exception e) {
            fail(e.toString() + ", look at your terminal / debug console for more detail");
			e.printStackTrace();
        }
        assertEquals(medicalRecords.toString(), medicalRecordsWrited.toString());
    }

}
