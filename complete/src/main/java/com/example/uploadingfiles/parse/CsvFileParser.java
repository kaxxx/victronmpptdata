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
                    //System.out.println("Data [date= " + mkDbDate(dbdate) + " , Yield(Wh)=" + dataLine[5] + "]");
                    //insertDB(dataLine);
                    printData(dataLine);
                    addSolarDataObject(dataLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void printData(String[] dataLine){
        System.out.println("\n");
        System.out.println("Date:"+dataLine[1]); //int
        System.out.println("Yield(Wh):"+dataLine[2]); //int
        System.out.println("Consumption(Wh):"+dataLine[3]); //int
        System.out.println("Max. PV power(W):"+dataLine[4]); //float
        System.out.println("Max. PV voltage(V):"+dataLine[5]); //float
        System.out.println("Min. battery voltage(V):"+dataLine[6]); //float
        System.out.println("Max. battery voltage(V):"+dataLine[7]); //float
        System.out.println("Time in bulk(m):"+dataLine[8]); //int
        System.out.println("Time in absorption(m):"+dataLine[9]); //int
        System.out.println("Time in float(m):"+dataLine[10]); //int
        System.out.println("Last error:"+dataLine[11]); //int
        System.out.println("2nd last error:"+dataLine[12]); //int
        System.out.println("3rd last error:"+dataLine[13]); //int
        System.out.println("4th last error:"+dataLine[14]); //int
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

        //SolarDataInsertRepository insertRepo = new SolarDataInsertRepository();
        //insertRepo.insertWithQuery(sData);
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
