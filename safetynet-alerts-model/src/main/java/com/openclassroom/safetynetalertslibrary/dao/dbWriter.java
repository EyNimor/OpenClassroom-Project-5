package com.openclassroom.safetynetalertslibrary.dao;

import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

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
    
}
