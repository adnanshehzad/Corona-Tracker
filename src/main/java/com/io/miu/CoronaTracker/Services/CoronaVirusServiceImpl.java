package com.io.miu.CoronaTracker.Services;

import com.io.miu.CoronaTracker.Models.CoronaData;
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
public class CoronaVirusServiceImpl  {
    private List<CoronaData> allstats=new ArrayList<>();
    private String coronadataurl_confirmed="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

        public List<CoronaData> fetchdata() throws IOException, InterruptedException {
            List<CoronaData> coronaDatanewList=new ArrayList<CoronaData>();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(coronadataurl_confirmed))
                    .build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(httpResponse.body());
            StringReader stringReader = new StringReader(httpResponse.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
            for (CSVRecord record : records) {
                int totalcases=0;
                CoronaData coronaData=new CoronaData();
                coronaData.setState(record.get("Province/State"));
                coronaData.setCountry(record.get("Country/Region"));
                String check=record.get(record.size()-1);
                if (!(check.isEmpty() || check==null))
                    totalcases=Integer.parseInt(record.get(record.size()-1));
                coronaData.setLatestTotalCases(totalcases);
                coronaDatanewList.add(coronaData);
            }
            this.allstats=coronaDatanewList;
            return (this.allstats);
        }
}
