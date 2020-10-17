package com.example.uploadingfiles.db;

import com.example.uploadingfiles.model.SolarData;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolarDataRepository extends JpaRepository<SolarData, Integer> {
}
