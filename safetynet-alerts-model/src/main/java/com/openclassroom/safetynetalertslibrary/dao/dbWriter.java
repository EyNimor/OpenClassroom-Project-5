package com.openclassroom.safetynetalertslibrary.dao;

import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

import com.openclassroom.safetynetalertslibrary.model.FireStations;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class dbWriter {

    public static void writePersonsToJsonFile(String filename, List<Persons> personsList) {
        try {
            JSONObject jsonObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray personsArray = new JSONArray();

            jsonObject.remove("persons");
            for(int i = 0; i <= (personsList.size() - 1); i++) { personsArray.add(personsList.get(i).toJsonObject()); }
            jsonObject.put("persons", personsArray);

            FileWriter writer = new FileWriter(filename);
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        } 
        catch(Exception e) { e.printStackTrace(); }
	}

    public static void writeFireStationsToJsonFile(String filename, List<FireStations> fireStationsList) {
        try {
            JSONObject jsonObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray fireStationsArray = new JSONArray();

            jsonObject.remove("firestations");
            for(int i = 0; i <= (fireStationsList.size() - 1); i++) { fireStationsArray.add(fireStationsList.get(i).toJsonObject()); }
            jsonObject.put("firestations", fireStationsArray);

            FileWriter writer = new FileWriter(filename);
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        } 
        catch(Exception e) { e.printStackTrace(); }
	}

    public static void writeMedicalRecordsToJsonFile(String filename, List<MedicalRecords> medicalRecordsList) {
        try {
            JSONObject jsonObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray medicalRecordsArray = new JSONArray();

            jsonObject.remove("medicalrecords");
            for(int i = 0; i <= (medicalRecordsList.size() - 1); i++) { medicalRecordsArray.add(medicalRecordsList.get(i).toJsonObject()); }
            jsonObject.put("medicalrecords", medicalRecordsArray);

            FileWriter writer = new FileWriter(filename);
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        } 
        catch(Exception e) { e.printStackTrace(); }
	}
    
}
