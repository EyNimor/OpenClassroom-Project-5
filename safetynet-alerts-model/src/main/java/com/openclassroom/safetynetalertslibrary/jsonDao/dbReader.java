package com.openclassroom.safetynetalertslibrary.jsonDao;

import java.io.FileReader;

import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class dbReader {

    public static Object readJsonFile(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
		JSONParser parser = new JSONParser();
        return parser.parse(reader);
    }
    
}
