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
public class EmExp {//employee experian
    public int Id ;
    public int emId;
    public String comName ;
    public String title;
    public String respon;
    public String period;



    public EmExp() {
        
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
     * @return the comName
     */
    public String getComName() {
        return comName;
    }

    /**
     * @param comName the comName to set
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the respon
     */
    public String getRespon() {
        return respon;
    }

    /**
     * @param respon the respon to set
     */
    public void setRespon(String respon) {
        this.respon = respon;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }
}
