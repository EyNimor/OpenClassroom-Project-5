package com.openclassroom.safetynetalertslibrary.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.simple.JSONObject;

@Entity
@Table(name = "FireStations")
public class FireStations {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String address;

    @NotNull
    private Integer station;

    public FireStations() {}

    public FireStations(Integer id, String address, Integer station) {
        this.id = id;
        this.address = address;
        this.station = station;
    }

    public FireStations(String address, Integer station) {
        this.address = address;
        this.station = station;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
    
    @Override
    public String toString() {
        return "FireStations [address=" + address + ", station=" + station + "]";
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        jsonObject.put("station", station);
        return jsonObject;
    }
    
}
