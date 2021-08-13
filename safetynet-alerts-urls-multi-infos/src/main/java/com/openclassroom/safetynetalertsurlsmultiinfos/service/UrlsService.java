package com.openclassroom.safetynetalertsurlsmultiinfos.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.openclassroom.safetynetalertslibrary.dao.FireStationDao;
import com.openclassroom.safetynetalertslibrary.dao.MedicalRecordsDao;
import com.openclassroom.safetynetalertslibrary.dao.PersonsDao;
import com.openclassroom.safetynetalertslibrary.jsonDao.dbReader;
import com.openclassroom.safetynetalertslibrary.model.FireStations;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.HouseURL;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.PersonURL;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.FireStationCovering;
import com.openclassroom.safetynetalertsurlsmultiinfos.model.HouseCovered;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class UrlsService {

    private static final Logger logger = LogManager.getLogger("UrlsService");

    @Autowired
    protected PersonsDao pDao;
    @Autowired
    protected FireStationDao fsDao;
    @Autowired
    protected MedicalRecordsDao mrDao;

    public Persons savePerson(Persons personToSave) {
        logger.info("Person - Sauvegarde : " + personToSave.toString());
        boolean saved = false;
        Persons personSaved = new Persons();
        try { 
            personSaved = pDao.save(personToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved == false) {
            return null;
        }
        else {
            return personSaved;
        }
    }

    public FireStations saveFireStation(FireStations fireStationToSave) {
        logger.info("FireStation - Sauvegarde : " + fireStationToSave.toString());
        boolean saved = false;
        FireStations fireStationSaved = new FireStations();
        try { 
            fireStationSaved = fsDao.save(fireStationToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved == false) {
            return null;
        }
        else {
            return fireStationSaved;
        }
    }

    public MedicalRecords saveMedicalRecords(MedicalRecords medicalRecordsToSave) {
        logger.info("MedicalRecords - Sauvegarde : " + medicalRecordsToSave.toString());
        boolean saved = false;
        MedicalRecords medicalRecordsSaved = new MedicalRecords();
        try { 
            medicalRecordsSaved = mrDao.save(medicalRecordsToSave);
            saved = true;
        }
        catch(IllegalArgumentException e) { 
            throw e;
        }
        if(saved == false) {
            return null;
        }
        else {
            return medicalRecordsSaved;
        }
    }

    public Integer computePersonAge(String birthdateString, LocalDateTime now) {
        List<String> birthdateList = new ArrayList<String>(Arrays.asList(birthdateString.split("/")));
        LocalDateTime birthdate = LocalDateTime.of(Integer.parseInt(birthdateList.get(2)), Month.of(Integer.parseInt(birthdateList.get(0))), Integer.parseInt(birthdateList.get(1)), 0, 0);
        Integer age = now.getYear() - birthdate.getYear();
        if(now.getMonth().getValue() < birthdate.getMonth().getValue()) {
            age = age - 1;
        }
        else if(now.getMonth().getValue() == birthdate.getMonth().getValue() && now.getDayOfMonth() < birthdate.getDayOfMonth()) {
            age = age - 1;
        }
        return age;
    }

    public MappingJacksonValue filterObject(Object objectToFilter, String[] things, boolean toKeep) {
        if (toKeep == true) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(things);
            FilterProvider filters = new SimpleFilterProvider().addFilter("personFilter", filter);
            MappingJacksonValue filteredObject = new MappingJacksonValue(objectToFilter);
            filteredObject.setFilters(filters);
            return filteredObject;
        }
        else {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(things);
            FilterProvider filters = new SimpleFilterProvider().addFilter("personFilter", filter);
            MappingJacksonValue filteredObject = new MappingJacksonValue(objectToFilter);
            filteredObject.setFilters(filters);
            return filteredObject;
        }
    }

    public MappingJacksonValue personsCoveredByThisStation(Integer stationNumber) {
        List<FireStations> fireStations = fsDao.findByStation(stationNumber);
        List<HouseURL> houseList = new ArrayList<>();
        Integer adultCount = 0;
        Integer childCount = 0;
        HouseCovered urlObject;

        try {
            for(int i = 0 ; i < fireStations.size() ; i++) {
                List<Persons> personsCovered = pDao.findByAddress(fireStations.get(i).getAddress());
                List<PersonURL> personList = new ArrayList<>();
                for(int u = 0 ; u < personsCovered.size() ; u++) {
                    PersonURL personURL = new PersonURL(personsCovered.get(u));
                    personList.add(personURL);

                    Integer personAge = computePersonAge(mrDao.findByFirstNameAndLastName(personURL.getFirstName(), personURL.getLastName()).getBirthdate(), LocalDateTime.now());
                    if(personAge < 18) {
                        childCount = childCount + 1;
                    }
                    else {
                        adultCount = adultCount + 1;
                    }
                }
                HouseURL house = new HouseURL();
                house.setAddress(fireStations.get(i).getAddress());
                house.setPersonList(personList);

                houseList.add(house);
            }

            urlObject = new HouseCovered(houseList, adultCount, childCount);

            String[] thingsToKeep = {"firstName", "lastName", "phone"};
            MappingJacksonValue filteredUrlObject = filterObject(urlObject, thingsToKeep, true);

            return filteredUrlObject;
        } catch(Exception e) {
            throw e;
        }
    }

    public MappingJacksonValue childHouseAtThisAddress(String address) {
        List<Persons> inhabitant = pDao.findByAddress(address);
        List<PersonURL> personList = new ArrayList<>();
        HouseURL house = new HouseURL();
        boolean houseContainAChild = false ;

        for(int i = 0 ; i < inhabitant.size() ; i++ ) {
            MedicalRecords personMedicalRecords = mrDao.findByFirstNameAndLastName(inhabitant.get(i).getFirstName(), inhabitant.get(i).getLastName());
            PersonURL personURL = new PersonURL(inhabitant.get(i), personMedicalRecords);
            personList.add(personURL);
        }

        for(int i = 0 ; i < personList.size() ; i++) {
            if(personList.get(i).getAge() < 18) {
                houseContainAChild = true;
                i = personList.size();
            }
        }

        if (houseContainAChild == true) {
            house.setAddress(address);
            house.setPersonList(personList);

            String[] thingsToKeep = {"firstName", "lastName", "age"};
            MappingJacksonValue filteredHouse = filterObject(house, thingsToKeep, true);

            return filteredHouse;
        }
        else {
            return null;
        }
    }

    public List<String> phoneOfPersonsCoveredByThisStation(Integer stationNumber) {
        List<FireStations> fireStations = fsDao.findByStation(stationNumber);
        List<Persons> personsCovered = new ArrayList<>();
        List<String> phoneList = new ArrayList<>();

        for(int i = 0 ; i < fireStations.size() ; i++) {
            personsCovered.addAll(pDao.findByAddress(fireStations.get(i).getAddress()));
        }

        for(int i = 0 ; i < personsCovered.size() ; i++) {
            phoneList.add(personsCovered.get(i).getPhone());
        }

        return phoneList;
    }

    public MappingJacksonValue personsAtThisAddressAndStationCoveringThisAddress(String address) {
        FireStations coveringFireStation = fsDao.findByAddress(address);
        List<Persons> personsCovered = pDao.findByAddress(address);
        List<PersonURL> personList = new ArrayList<>();
        FireStationCovering fireStationCovering = new FireStationCovering();

        for(int i = 0 ; i < personsCovered.size() ; i++) {
            MedicalRecords personMedicalRecords = mrDao.findByFirstNameAndLastName(personsCovered.get(i).getFirstName(), personsCovered.get(i).getLastName());
            PersonURL personURL = new PersonURL(personsCovered.get(i), personMedicalRecords);
            personList.add(personURL);
        }

        fireStationCovering.setStationNumber(coveringFireStation.getStation());
        fireStationCovering.setPersonList(personList);

        String[] thingsToFilterOut = {"email"};
        MappingJacksonValue filteredFireStationCovering = filterObject(fireStationCovering, thingsToFilterOut, false);

        return filteredFireStationCovering;
    }

    public MappingJacksonValue personsCoveredByEachStation(List<Integer> stationNumberList) {
        List<FireStations> coveringFireStations = new ArrayList<>();
        List<HouseURL> personsCoveredSortedByAddress = new ArrayList<>();

        for(int i = 0 ; i < stationNumberList.size() ; i++) {
            coveringFireStations.addAll(fsDao.findByStation(stationNumberList.get(i)));
        }

        for(int i = 0 ; i < coveringFireStations.size() ; i++) {
            List<PersonURL> personList = new ArrayList<>();
            HouseURL houseURL = new HouseURL();
            List<Persons> personsCovered = pDao.findByAddress(coveringFireStations.get(i).getAddress());
            for(int u = 0 ; u < personsCovered.size() ; u++) {
                PersonURL personURL = new PersonURL(personsCovered.get(u), mrDao.findByFirstNameAndLastName(personsCovered.get(u).getFirstName(), personsCovered.get(u).getLastName()));
                personList.add(personURL);
            }

            houseURL.setAddress(coveringFireStations.get(i).getAddress());
            houseURL.setPersonList(personList);

            personsCoveredSortedByAddress.add(houseURL);
        }
        String[] thingsToFilterOut = {"email"};
        MappingJacksonValue filteredPersonsCoveredSortedByAddress = filterObject(personsCoveredSortedByAddress, thingsToFilterOut, false);

        return filteredPersonsCoveredSortedByAddress;
    }

    public MappingJacksonValue personsInformations(String firstName, String lastName) {
        Persons personsFinded = pDao.findByFirstNameAndLastName(firstName, lastName);
        MedicalRecords personsMedicalRecords = mrDao.findByFirstNameAndLastName(firstName, lastName);
        PersonURL person = new PersonURL(personsFinded, personsMedicalRecords);
        HouseURL personInformations = new HouseURL();

        List<PersonURL> personList = new ArrayList<>();
        personList.add(person);

        personInformations.setAddress(personsFinded.getAddress());
        personInformations.setPersonList(personList);

        String[] thingsToFilterOut = {"phone"};
        MappingJacksonValue filteredPersonInformations = filterObject(personInformations, thingsToFilterOut, false);
        return filteredPersonInformations;
    }

    public void recoverDatabaseFromJSON(File filename) throws Exception {
        logger.info("Récupération du .JSON - " + filename);
        try {
            JSONObject dbObject = (JSONObject) dbReader.readJsonFile(filename);
            JSONArray jsonArray = (JSONArray) dbObject.get("persons");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
                JSONObject personJSON = (JSONObject) jsonArray.get(i);
                Persons personToSave = new Persons((String) personJSON.get("firstName"),
                                                    (String) personJSON.get("lastName"),
                                                    (String) personJSON.get("address"),
                                                    (String) personJSON.get("city"),
                                                    (String) personJSON.get("phone"),
                                                    (String) personJSON.get("email"),
                                                    (String) personJSON.get("zip"));
                savePerson(personToSave);
            }

            dbObject = (JSONObject) dbReader.readJsonFile(filename);
            jsonArray = (JSONArray) dbObject.get("firestations");
            for(int i = 0 ; i <= jsonArray.size() - 1 ; i++) {
                JSONObject fireStationJSON = (JSONObject) jsonArray.get(i);
                FireStations fireStationToSave = new FireStations((String) fireStationJSON.get("address"),
                                                                Integer.parseInt(fireStationJSON.get("station").toString()));
                saveFireStation(fireStationToSave);
            }

            dbObject = (JSONObject) dbReader.readJsonFile(filename);
            jsonArray = (JSONArray) dbObject.get("medicalrecords");
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
                MedicalRecords medicalRecordsToSave = new MedicalRecords((String) medicalRecordsJSON.get("firstName"),
                                                    (String) medicalRecordsJSON.get("lastName"),
                                                    (String) medicalRecordsJSON.get("birthdate"),
                                                    medications,
                                                    allergies);
                saveMedicalRecords(medicalRecordsToSave);
            }
        }
        catch(Exception e) {
            throw e;
        }
    }
    
}
