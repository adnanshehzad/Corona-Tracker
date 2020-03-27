package com.io.miu.CoronaTracker.Controllers;

import com.io.miu.CoronaTracker.Models.CoronaData;
import com.io.miu.CoronaTracker.Services.impl.CoronaconfirmedServiceImpl;
import com.io.miu.CoronaTracker.Services.impl.CoronarecoveredServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recovered")
public class Coronarecoveredcontroller {
    @Autowired
    CoronarecoveredServiceImpl coronarecoveredService;

    @GetMapping("/")
    public ResponseEntity<?> getAllData() throws IOException, InterruptedException {
        List<CoronaData> resultData=coronarecoveredService.fetchcasesdata();
        if (!resultData.isEmpty()){
            return new ResponseEntity<>(resultData, HttpStatus.OK);
        }
        return new ResponseEntity<String>("No data found",HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/{country}")
    public ResponseEntity<?> getByCountry(@PathVariable("country") String country) throws IOException, InterruptedException {
        CoronaData countrydata=coronarecoveredService.getcasesByCountry(country);
        if (countrydata!=null){
            return new ResponseEntity<CoronaData>(countrydata,HttpStatus.OK);
        }
        else {
            HashMap<String,String> error=new HashMap<>();
            error.put("message","This Country data does,not exist or you write a wrong Country");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
