package com.openclassroom.safetynetalertsurlsmultiinfos.model;

import java.util.List;

public class FireStationCovering {

    private List<PersonURL> personList;

    private Integer stationNumber;

    public FireStationCovering() {}

    public FireStationCovering(List<PersonURL> personList, Integer stationNumber) {
        this.personList = personList;
        this.stationNumber = stationNumber;
    }

    public List<PersonURL> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonURL> personList) {
        this.personList = personList;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String toString() {
        return "FireStationCovering [personList=" + personList.toString() + ", stationNumber=" + stationNumber + "]";
    }
    
}
