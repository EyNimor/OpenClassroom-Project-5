package com.openclassroom.safetynetalertslibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.openclassroom.safetynetalertslibrary.annotations.ExcludeFromJacocoGeneratedReport;

import org.json.simple.JSONObject;

/**
 * <p>The {@code Persons} Class contains six informations about a person : </p>
 * <ul>
 * <li>First Name</li>
 * <li>Last Name</li>
 * <li>Address</li>
 * <li>City</li>
 * <li>Phone</li>
 * <li>Email</li>
 * </ul>
 * <p>Have a full person profile require a {@code Persons} value, and a {@code MedicalRecords} value. To link a {@code Persons} to a 
 * {@code MedicalRecords}, only the FirstName and the LastName value can be used, because they are the only two value that are common
 * to {@code Persons} and {@code MedicalRecords} class. Consequently, the FirstName and the LastName can't be identical between two
 * {@code Persons} variable.</p>
 * @see com.openclassroom.safetynetalertslibrary.model.MedicalRecords
 * @author Yves Lesaque
 */
@Entity
@Table(name = "Persons")
@ExcludeFromJacocoGeneratedReport
public class Persons {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String firstName,
                 lastName,
                 address,
                 city,
                 phone,
                 email,
                 zip;

    public Persons() {}

    public Persons(Integer id, String firstName, String lastname, String address, String city, String phone, String email, String zip) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastname;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.zip = zip;
    }

    public Persons(String firstName, String lastname, String address, String city, String phone, String email, String zip) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.zip = zip;
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

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Persons [address=" + address + ", city=" + city + ", email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phone=" + phone + ", zip=" + zip + "]";
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("address", address);
        jsonObject.put("city", city);
        jsonObject.put("phone", phone);
        jsonObject.put("email", email);
        jsonObject.put("zip", zip);
        return jsonObject;
    }

}
