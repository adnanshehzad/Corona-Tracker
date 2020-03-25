package com.io.miu.CoronaTracker.Services.impl;

import com.io.miu.CoronaTracker.Models.CoronaData;
import com.io.miu.CoronaTracker.Services.CoronaVirusService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusServiceImpl  implements CoronaVirusService {
    private List<CoronaData> allstats=new ArrayList<>();
    private String coronadataurl_confirmed="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private Iterable<CSVRecord> records;

    public void getdataonServerStart() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(coronadataurl_confirmed))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(httpResponse.body());
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
    }
    public int checkLatestCases( CSVRecord record){
        int totalcases=0;
        String check=record.get(record.size()-1);
        if (!(check.isEmpty() || check==null))
            totalcases=Integer.parseInt(record.get(record.size()-1));
        return totalcases;
    }
    public List<CoronaData> fetchdata() throws IOException, InterruptedException {
            getdataonServerStart();
            List<CoronaData> coronaDatanewList=new ArrayList<CoronaData>();
            for (CSVRecord record : records) {
                CoronaData coronaData=new CoronaData();
                coronaData.setState(record.get("Province/State"));
                coronaData.setCountry(record.get("Country/Region"));
                coronaData.setLatestTotalCases(checkLatestCases(record));
                coronaDatanewList.add(coronaData);
            }
            this.allstats=coronaDatanewList;
            return (this.allstats);
        }

    public CoronaData getByCountry(String country) throws IOException, InterruptedException {
        getdataonServerStart();
        CoronaData data=new CoronaData();
            for(CSVRecord record:records){
                String region=record.get("Country/Region");
                if (region.equalsIgnoreCase(country)){
                    data.setCountry(record.get("Country/Region"));
                    data.setState(record.get("Province/State"));
                    data.setLatestTotalCases(checkLatestCases(record));
                }
            }
            return data;
    }
}
