package com.learn.springbootproject.models;

public class LocationStats {

    private String state;
    private String country;
    private int totalCases;
    private int newCases;

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalCases=" + totalCases +
                ", newCases=" + newCases +
                '}';
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
