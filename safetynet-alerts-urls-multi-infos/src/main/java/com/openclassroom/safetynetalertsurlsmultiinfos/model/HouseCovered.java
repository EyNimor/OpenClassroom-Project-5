package com.openclassroom.safetynetalertsurlsmultiinfos.model;

import java.util.List;

public class HouseCovered {
    
    private List<HouseURL> houseList ;

    private Integer adultCount,
                    childCount;

    public HouseCovered() {}

    public HouseCovered(List<HouseURL> houseList, Integer adultCount, Integer childCount) {
        this.houseList = houseList;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

    public List<HouseURL> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseURL> houseList) {
        this.houseList = houseList;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    @Override
    public String toString() {
        return "HouseCovered [adultCount=" + adultCount + ", childCount=" + childCount + ", houseList=" + houseList.toString()
                + "]";
    }
    
}
