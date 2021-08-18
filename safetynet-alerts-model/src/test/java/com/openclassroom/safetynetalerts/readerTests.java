package com.openclassroom.safetynetalerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import com.openclassroom.safetynetalertslibrary.jsonDao.dbReader;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = dbReader.class)
class readerTests extends dbReader {

	File filename = new File("src/test/resources/readerTestFile.json");

	@Test
	void readerTest() {
		try {
			JSONObject jsonObject = (JSONObject) readJsonFile(filename);
			String expectedObject = "Test successful!";
			String returnedObject = (String) jsonObject.get("readerTestString");
			assertEquals(expectedObject, returnedObject);
		} catch (Exception e) {
			fail(e.toString() + ", look at your terminal / debug console for more detail");
			e.printStackTrace();
		}
	}

}
