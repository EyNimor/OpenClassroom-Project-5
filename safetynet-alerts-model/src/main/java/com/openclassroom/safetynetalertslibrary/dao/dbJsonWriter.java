package com.openclassroom.safetynetalertslibrary.dao;

import java.io.FileWriter;
import java.util.List;

import com.openclassroom.safetynetalertslibrary.model.Persons;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class dbJsonWriter {

    public static void writeJsonFile(String filename, List<Persons> personsList) {
        JSONObject jsonObject = new JSONObject();
        JSONArray personsArray = new JSONArray();

        for(int i = 0; i <= (personsList.size() - 1); i++) { personsArray.add(personsList.get(i).toJsonObject()); }
        jsonObject.put("persons", personsArray);

        try {  
            FileWriter writer = new FileWriter(filename);
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
	}
    
}
