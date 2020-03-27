package com.io.miu.CoronaTracker.Models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class CoronaData {
    private String state;
    private String Country;
    private int latestTotalCases;
    private double Lat;
    private double Long;
    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }



}
