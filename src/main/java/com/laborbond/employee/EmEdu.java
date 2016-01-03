/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.employee;

/**
 *
 * @author Fu
 */
public class EmEdu {
    public int Id ;
    public int emId;
    public String academy ;
    public String major;
    public String year;
    public String info;



    public EmEdu() {
        
    }

    
    public boolean isValid(){
        return getId()!=-1;
    }

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the emId
     */
    public int getEmId() {
        return emId;
    }

    /**
     * @param emId the emId to set
     */
    public void setEmId(int emId) {
        this.emId = emId;
    }

    /**
     * @return the academy
     */
    public String getAcademy() {
        return academy;
    }

    /**
     * @param academy the academy to set
     */
    public void setAcademy(String academy) {
        this.academy = academy;
    }

    /**
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }
}