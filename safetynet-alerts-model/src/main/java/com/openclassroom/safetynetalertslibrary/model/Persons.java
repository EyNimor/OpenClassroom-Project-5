package com.openclassroom.safetynetalertslibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;

/**
 * <p>Class thay contains informations about a person. Composed of : </p>
 * <ul>
 * <li>First Name</li>
 * <li>Last Name</li>
 * <li>Address</li>
 * <li>City</li>
 * <li>Phone</li>
 * <li>Email</li>
 * </ul>
 * <p>An ID value is generated automatically when a Persons var is created. Permit the interaction with a SQL Database.</p>
 */
@Entity
@Table(name = "Persons")
public class Persons {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String firstName,
                 lastName,
                 address,
                 city,
                 phone,
                 email;
                 
    @NotNull
    private Long zip;

    public Persons() {}

    public Persons(Integer id, String firstName, String lastname, String address, String city, String phone, String email, Long zip) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastname;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.zip = zip;
    }

    public Persons(String firstName, String lastname, String address, String city, String phone, String email, Long zip) {
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

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Persons [id= " + id + ", address=" + address + ", city=" + city + ", email=" + email + ", firstName=" + firstName
                + ", lastname=" + lastName + ", phone=" + phone + ", zip=" + zip + "]";
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastname", lastName);
        jsonObject.put("address", address);
        jsonObject.put("city", city);
        jsonObject.put("phone", phone);
        jsonObject.put("email", email);
        jsonObject.put("zip", zip);
        return jsonObject;
    }

}
