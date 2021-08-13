package com.openclassroom.safetynetalertslibrary.jsonDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class dbReader {

    public static Object readJsonFile(File filename) throws Exception {
        if(filename.exists() == false) {
            throw new FileNotFoundException("The file does not exist, or the file path is incorrect :" + filename.getName());
        }
        else if(filename.canRead() == false) {
            throw new FileNotFoundException("The file is unreadable :" + filename.getName());
        }
        else {
            FileReader reader = new FileReader(filename);
		    JSONParser parser = new JSONParser();
            return parser.parse(reader);
        }
    }
    
}
