package com.io.miu.CoronaTracker.Models;

public class CoronaDataBuilder {
    private String state;
    private String Country;
    private int latestTotalCases;
    private double Lat;
    private double Longi;

    public CoronaDataBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public CoronaDataBuilder setCountry(String country) {
        Country = country;
        return this;
    }

    public CoronaDataBuilder setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
        return this;
    }
    public CoronaDataBuilder setLatitude(double lat){
        this.Lat=lat;
        return this;
    }
    public CoronaDataBuilder setLongitude(double lon){
        this.Longi=lon;
        return this;
    }

    public CoronaData buildCoronaData(){
        return new CoronaData(state,Country,latestTotalCases,Lat,Longi);
    }
}
