package com.io.miu.CoronaTracker.Services;

import com.io.miu.CoronaTracker.Models.CoronaData;

import java.io.IOException;
import java.util.List;

public interface CoronaVirusService {
    List<CoronaData> fetchdata() throws IOException, InterruptedException;
    CoronaData getByCountry(String country) throws IOException, InterruptedException;
    void getdataonServerStart() throws IOException, InterruptedException;
}
