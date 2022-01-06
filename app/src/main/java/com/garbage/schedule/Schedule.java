package com.garbage.schedule;

public class Schedule {
    private String date;
    private GarbageType garbageType;
    private String district;
    private String street;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GarbageType getGarbageType() {
        return garbageType;
    }

    public void setGarbageType(GarbageType garbageType) {
        this.garbageType = garbageType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
