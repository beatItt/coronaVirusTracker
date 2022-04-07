package com.learn.springbootproject.service;

import com.learn.springbootproject.models.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


@Service
public class CoronaDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats=new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void setAllStats(List<LocationStats> allStats) {
        this.allStats = allStats;
    }

    @PostConstruct
    @Scheduled(cron="* * 1 * * *")//run everyday on 1st hour, might miss data if data source is updated earlier for the day
    public void fetchVirusData() throws IOException, InterruptedException {
         List<LocationStats> newStats=new ArrayList<>();

        //System.out.println("calling data source");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()//builder pattern
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());

        StringReader csvStringReader=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvStringReader);//header with auto detection(1st line is header)


        for (CSVRecord record : records) {
            LocationStats locationStats=new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setTotalCases(Integer.parseInt(record.get(record.size()-1)));
            int currentTotalCases=Integer.parseInt(record.get(record.size()-1));
            int prevDayCases=Integer.parseInt(record.get(record.size()-2));
            int newCases=currentTotalCases-prevDayCases;
            locationStats.setNewCases(newCases);
            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats=newStats;// data saved to model LocationStats
        //for concurrency, temp list newStats once totally constructed is assigned to allStats, so that users dont get 404 etc
        //during constructing of new list, users seeing old data is fine THAN 404

    }


}




