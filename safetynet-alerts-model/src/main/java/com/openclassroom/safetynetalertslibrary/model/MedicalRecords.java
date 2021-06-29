package com.openclassroom.safetynetalertslibrary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "MedicalRecords")
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String firstName,
                    lastName,
                    birthdate;

    private String medications,
                    allergies;

    public MedicalRecords() {}

    public MedicalRecords(String firstName, String lastName, String birthdate, List<String> medications,
            List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;

        if(medications.isEmpty()) {
            this.medications = null;
        }
        else {
            this.medications = medications.toString();
            this.medications = this.medications.substring(1, this.medications.length()-1);
            this.medications.replaceAll("\"", "");
        }
        
        if(allergies.isEmpty()) {
            this.allergies = null;
        }
        else {
            this.allergies = allergies.toString();
            this.allergies = this.allergies.substring(1, this.allergies.length()-1);
            this.allergies.replaceAll("\"", "");
        }
    }

    public MedicalRecords(Integer id, String firstName, String lastName, String birthdate, List<String> medications,
            List<String> allergies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;

        if(medications.isEmpty()) {
            this.medications = null;
        }
        else {
            this.medications = medications.toString();
            this.medications = this.medications.substring(1, this.medications.length()-1);
        }
        
        if(allergies.isEmpty()) {
            this.allergies = null;
        }
        else {
            this.allergies = allergies.toString();
            this.allergies = this.allergies.substring(1, this.allergies.length()-1);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        List<String> medications;
        if(this.medications == null) {
             medications = new ArrayList<>();
        }
        else {
            medications = new ArrayList<String>(Arrays.asList(this.medications.split(", ")));
        }
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications.toString();
        this.medications = this.medications.substring(1, this.medications.length()-1);
        this.medications.replaceAll("\"", "");
    }

    public List<String> getAllergies() {
        List<String> allergies;
        if(this.allergies == null) {
            allergies = new ArrayList<>();
        }
        else {
            allergies = new ArrayList<String>(Arrays.asList(this.allergies.split(", ")));
        }
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies.toString();
        this.allergies = this.allergies.substring(1, this.allergies.length()-1);
        this.allergies.replaceAll("\"", "");
    }

    @Override
    public String toString() {
        return "MedicalRecords [allergies=" + allergies + ", birthdate=" + birthdate + ", firstName=" + firstName
                + ", lastName=" + lastName + ", medications=" + medications + "]";
    }
    
    public JSONObject toJsonObject() {
        List<String> medications = getMedications();
        List<String> allergies = getAllergies();
        JSONObject jsonObject = new JSONObject();
        JSONArray medicationsArray = new JSONArray();
        JSONArray allergiesArray = new JSONArray();

        medicationsArray.addAll(medications);
        allergiesArray.addAll(allergies);

        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("birthdate", birthdate);
        jsonObject.put("medications", medicationsArray);
        jsonObject.put("allergies", allergiesArray);

        return jsonObject;
    }

}
