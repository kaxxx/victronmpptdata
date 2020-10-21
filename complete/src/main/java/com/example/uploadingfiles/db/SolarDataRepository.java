package com.example.uploadingfiles.db;

import com.example.uploadingfiles.model.SolarData;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolarDataRepository extends JpaRepository<SolarData, Integer> {
    @Query("SELECT sd.date, sd.minBatteryVoltage  FROM SolarData sd")
    public List<String> getMinBatVolList();

    @Query("SELECT sd.date, sd.minBatteryVoltage FROM SolarData sd WHERE sd.date between ?1 AND ?2")
    public List<String> getMinBatVolList(int from, int to);

}
