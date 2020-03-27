package com.io.miu.CoronaTracker.Services;

import com.io.miu.CoronaTracker.Models.CoronaData;

import java.io.IOException;
import java.util.List;

public interface ICoronaService {
    List<CoronaData> fetchcasesdata() throws IOException, InterruptedException;
    CoronaData getcasesByCountry(String country) throws IOException, InterruptedException;
    void getdataonServerStart(String url) throws IOException, InterruptedException;
}
