package com.example.uploadingfiles;

import com.example.uploadingfiles.db.SolarDataRepository;
import com.example.uploadingfiles.model.SolarData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SolarDataController {
    @Autowired
    SolarDataRepository solarDataRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/person",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SolarData> getPerson(){

        List<SolarData> allDataList = (List<SolarData>) solarDataRepository.findAll();

        return allDataList;

    }
}
