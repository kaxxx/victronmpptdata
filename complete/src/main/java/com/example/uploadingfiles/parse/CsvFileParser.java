package com.example.uploadingfiles.parse;

import com.example.uploadingfiles.model.SolarData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CsvFileParser {

    File csvFile;
    List<SolarData> solarDataList = new ArrayList();

    public List<SolarData> getSolarDataList(){
        return solarDataList;
    }
    public void parse(Path path){
        if (path != null){
            csvFile = path.toFile();
            String line = "";
            String cvsSplitBy = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] dataLine = line.split(cvsSplitBy);
                    if (dataLine[0].equals("Days ago") || dataLine[0].equals("0")){
                        continue;
                    }
                    Date dbdate = parseDate(dataLine[1]);
                    dataLine[1] = mkDbDate(dbdate);
                    addSolarDataObject(dataLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void addSolarDataObject(String[] dataLine){

        SolarData sData = new SolarData();

        sData.setDate(Integer.valueOf(dataLine[1]));
        sData.setYield(Integer.valueOf(dataLine[2]));
        sData.setConsumption(Integer.valueOf(dataLine[3]));
        sData.setMaxPVpower(Float.parseFloat(dataLine[4]));
        sData.setMaxPVvoltage(Float.parseFloat(dataLine[5]));
        sData.setMinBatteryVoltage(Float.parseFloat(dataLine[6]));
        sData.setMaxBatteryVoltage(Float.parseFloat(dataLine[7]));
        sData.setTimeInBulk(Integer.valueOf(dataLine[8]));
        sData.setTimeInAbsorption(Integer.valueOf(dataLine[9]));
        sData.setIntTimeInFloat(Integer.valueOf(dataLine[10]));
        sData.setLastError(Integer.valueOf(dataLine[11]));
        sData.setSecondLastError(Integer.valueOf(dataLine[12]));
        sData.setThirdLastError(Integer.valueOf(dataLine[13]));
        sData.setFourthLastError(Integer.valueOf(dataLine[14]));

        solarDataList.add(sData);

    }

    public String mkDbDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public Date parseDate(String csvDate){
        DateFormat df = new SimpleDateFormat("MM/dd/yy", Locale.ENGLISH);

        Date result = null;
        try {
            result = df.parse(csvDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
