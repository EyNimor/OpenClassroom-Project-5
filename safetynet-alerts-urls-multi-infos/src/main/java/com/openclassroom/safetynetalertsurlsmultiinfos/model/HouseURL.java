package com.openclassroom.safetynetalertsurlsmultiinfos.model;

import java.util.List;

public class HouseURL {

    private String address;

    private List<PersonURL> personList;

    public HouseURL() {}

    public HouseURL(String address, List<PersonURL> personList) {
        this.address = address;
        this.personList = personList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonURL> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonURL> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "HouseURL [address=" + address + ", personList=" + personList.toString() + "]";
    }
    
}
