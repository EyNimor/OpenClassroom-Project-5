package com.openclassroom.safetynetalertslibrary.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.simple.JSONObject;

@Entity
@Table(name = "FireStations")
public class FireStations {

    @Id
    @GeneratedValue
    private Integer id;

    private String address;

    private Byte station;

    public FireStations(String address, Byte station) {
        this.address = address;
        this.station = station;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getStation() {
        return station;
    }

    public void setStation(Byte station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "fireStations [address=" + address + ", station=" + station + "]";
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        jsonObject.put("station", station);
        return jsonObject;
    }
    
}
