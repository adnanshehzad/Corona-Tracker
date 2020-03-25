package com.io.miu.CoronaTracker.Controllers;

import com.io.miu.CoronaTracker.Models.CoronaData;
import com.io.miu.CoronaTracker.Services.CoronaVirusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class Coronacontroller {
    @Autowired
    CoronaVirusServiceImpl coronaVirusService;

    @GetMapping("/")
    public ResponseEntity<?> getAllData() throws IOException, InterruptedException {
        List<CoronaData> resultData=coronaVirusService.fetchdata();
        if (!resultData.isEmpty()){
            return new ResponseEntity<>(resultData,HttpStatus.OK);
        }
        return new ResponseEntity<String>("No data found",HttpStatus.BAD_REQUEST);
    }
}
