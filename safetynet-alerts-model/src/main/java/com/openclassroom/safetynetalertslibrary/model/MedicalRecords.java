package com.openclassroom.safetynetalertslibrary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.openclassroom.safetynetalertslibrary.annotations.ExcludeFromJacocoGeneratedReport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>The {@code MedicalRecords} Class contains 5 medicals informations about a person : </p>
 * <ul>
 * <li>First Name</li>
 * <li>Last Name</li>
 * <li>Birthdate</li>
 * <li>Medications</li>
 * <li>Allergies</li>
 * </ul>
 * <p>Due to the impossibility of save a {@code List} variable in a SQL database, the Medications and the Allergies value
 * are type {@code String}, and the {@code MedicalRecords} class convert {@code List} to {@code String} during the constructor, and
 * when using a {@code setMedications(List<String> medications)} or a {@code setAllergies(List<String> allergies)} method. </p>
 * <p>Have a full person profile require a {@code Persons} value, and a {@code MedicalRecords} value. To link a {@code Persons} to a 
 * {@code MedicalRecords}, only the FirstName and the LastName value can be used, because they are the only two value that are common
 * to {@code Persons} and {@code MedicalRecords} class. Consequently, the FirstName and the LastName can't be identical between two
 * {@code Persons} variable. </p>
 * @see com.openclassroom.safetynetalertslibrary.model.Persons
 * @author Yves Lesaque
 */
@Entity
@Table(name = "MedicalRecords")
@ExcludeFromJacocoGeneratedReport
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
