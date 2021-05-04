package com.openclassroom.safetynetalertslibrary.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name = "MedicalRecords")
public class MedicalRecords {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName,
                    lastName;
    
    //Subject to change !
    private String birthdate;

    private List<String> medications,
                        allergies;

    public MedicalRecords(String firstName, String lastName, String birthdate, List<String> medications,
            List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public Integer getId() {
        return id;
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
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "medicalRecords [allergies=" + allergies + ", birthdate=" + birthdate + ", firstName=" + firstName
                + ", lastName=" + lastName + ", medications=" + medications + "]";
    }
    
}
