package com.openclassroom.safetynetalertsurlsmultiinfos.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.openclassroom.safetynetalertslibrary.annotations.ExcludeFromJacocoGeneratedReport;
import com.openclassroom.safetynetalertslibrary.model.MedicalRecords;
import com.openclassroom.safetynetalertslibrary.model.Persons;
import com.openclassroom.safetynetalertsurlsmultiinfos.service.UrlsService;

@JsonFilter("personFilter")
@ExcludeFromJacocoGeneratedReport
public class PersonURL {

    private String firstName,
                    lastName,
                    phone,
                    email;

    private Integer age;

    private List<String> allergies,
                        medications;

    public PersonURL() {}

    public PersonURL(String firstName, String lastName, String phone, String email, Integer age, List<String> allergies,
            List<String> medications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.allergies = allergies;
        this.medications = medications;
    }

    public PersonURL(Persons person) {
        firstName = person.getFirstName();
        lastName = person.getLastName();
        phone = person.getPhone();
        email = person.getEmail();
    }

    public PersonURL(Persons person, MedicalRecords personMedicalRecords) {
        UrlsService service = new UrlsService();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        phone = person.getPhone();
        email = person.getEmail();
        age = service.computePersonAge(personMedicalRecords.getBirthdate(), LocalDateTime.now());
        allergies = personMedicalRecords.getAllergies();
        medications = personMedicalRecords.getMedications();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "PersonURL [age=" + age + ", allergies=" + allergies + ", email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", medications=" + medications + ", phone=" + phone + "]";
    }
    
}
