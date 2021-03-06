package com.example.uploadingfiles.model;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class SolarData {
    @Id
    private int date;
    private  int yield;
    private int consumption;
    private float maxPVpower;
    private float maxPVvoltage;
    private float minBatteryVoltage;
    private float maxBatteryVoltage;
    private int timeInBulk;
    private int timeInAbsorption;
    private int intTimeInFloat;
    private int lastError;
    private int secondLastError;
    private int thirdLastError;
    private int fourthLastError;

    public int getDate() {
         return date;
    }

    /**
     * Used by charts.html
     * @return
     */
    public String getGdate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
        Date d = new Date();

        try {
            d = simpleDateFormat.parse(""+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        return simpleDateFormat.format(d);
    }

    /**
     * Used by table view on data.html
     * @return
     */
    public Date getDdate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();
        try {
            d = simpleDateFormat.parse(""+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    public void setDate(int date) {
        this.date = date;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public float getMaxPVpower() {
        return maxPVpower;
    }

    public void setMaxPVpower(float maxPVpower) {
        this.maxPVpower = maxPVpower;
    }

    public float getMaxPVvoltage() {
        return maxPVvoltage;
    }

    public void setMaxPVvoltage(float maxPVvoltage) {
        this.maxPVvoltage = maxPVvoltage;
    }

    public float getMinBatteryVoltage() {
        return minBatteryVoltage;
    }

    public void setMinBatteryVoltage(float minBatteryVoltage) {
        this.minBatteryVoltage = minBatteryVoltage;
    }

    public float getMaxBatteryVoltage() {
        return maxBatteryVoltage;
    }

    public void setMaxBatteryVoltage(float maxBatteryVoltage) {
        this.maxBatteryVoltage = maxBatteryVoltage;
    }

    public int getTimeInBulk() {
        return timeInBulk;
    }

    public void setTimeInBulk(int timeInBulk) {
        this.timeInBulk = timeInBulk;
    }

    public int getTimeInAbsorption() {
        return timeInAbsorption;
    }

    public void setTimeInAbsorption(int timeInAbsorption) {
        this.timeInAbsorption = timeInAbsorption;
    }

    public int getIntTimeInFloat() {
        return intTimeInFloat;
    }

    public void setIntTimeInFloat(int intTimeInFloat) {
        this.intTimeInFloat = intTimeInFloat;
    }

    public int getLastError() {
        return lastError;
    }

    public void setLastError(int lastError) {
        this.lastError = lastError;
    }

    public int getSecondLastError() {
        return secondLastError;
    }

    public void setSecondLastError(int secondLastError) {
        this.secondLastError = secondLastError;
    }

    public int getThirdLastError() {
        return thirdLastError;
    }

    public void setThirdLastError(int thirdLastError) {
        this.thirdLastError = thirdLastError;
    }

    public int getFourthLastError() {
        return fourthLastError;
    }

    public void setFourthLastError(int fourthLastError) {
        this.fourthLastError = fourthLastError;
    }
}
