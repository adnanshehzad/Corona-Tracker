package com.io.miu.CoronaTracker.Services.impl;

import com.io.miu.CoronaTracker.Models.CoronaData;
import com.io.miu.CoronaTracker.Models.CoronaDataBuilder;
import com.io.miu.CoronaTracker.Services.ICoronaService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronadeathServiceImpl implements ICoronaService {
    private List<CoronaData> allstats=new ArrayList<>();
    private String coronadataurl_death="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private Iterable<CSVRecord> records;

    @Override
    public List<CoronaData> fetchcasesdata() throws IOException, InterruptedException {
        getdataonServerStart(coronadataurl_death);
        List<CoronaData> coronaDatanewList=new ArrayList<CoronaData>();
        for (CSVRecord record : records) {
            //Applying builder pattern
            coronaDatanewList.add(new CoronaDataBuilder().setState(record.get("Province/State"))
                    .setCountry(record.get("Country/Region"))
                    .setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)))
                    .setLatitude(Double.parseDouble(record.get("Lat")))
                    .setLongitude(Double.parseDouble(record.get("Long")))
                    .buildCoronaData());
        }
        this.allstats=coronaDatanewList;
        return (this.allstats);
    }

    @Override
    public CoronaData getcasesByCountry(String country) throws IOException, InterruptedException {
        getdataonServerStart(coronadataurl_death);
        CoronaData data=new CoronaData();
        for(CSVRecord record:records){
            String region=record.get("Country/Region");
            if (region.equalsIgnoreCase(country)){
                //Applying Builder Pattern
                data=new CoronaDataBuilder()
                        .setCountry(record.get("Country/Region"))
                        .setState(record.get("Province/State"))
                        .setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)))
                        .setLatitude(Double.parseDouble(record.get("Lat")))
                        .setLongitude(Double.parseDouble(record.get("Long")))
                        .buildCoronaData();
            }
        }
        return data;
    }

    @Override
    public void getdataonServerStart(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(httpResponse.body());
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
    }
}
