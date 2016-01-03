/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.job;

import com.laborbond.company.ComInfo;


/**
 *
 * @author Fu
 */
public class Job {
    public int id ;
    public ComInfo offer ;
    public String title;
    public String info;
    public String location;
    public String request;
    public String respon;
    public String apply;
    public String industry;
    public int cmin;
    public int cmax;
    public String type;
    public long time;
    public int state;


    public Job() {
        
    }

    
    public boolean isValid(){
        return id!=-1;
    }

    /**
     * @return the Id
     */
    public int getId() {
        return id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.id = Id;
    }

    /**
     * @return the comId
     */


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

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
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
     * @return the apply
     */
    public String getApply() {
        return apply;
    }

    /**
     * @param apply the apply to set
     */
    public void setApply(String apply) {
        this.apply = apply;
    }

    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * @return the cmin
     */
    public int getCmin() {
        return cmin;
    }

    /**
     * @param cmin the cmin to set
     */
    public void setCmin(int cmin) {
        this.cmin = cmin;
    }

    /**
     * @return the cmax
     */
    public int getCmax() {
        return cmax;
    }

    /**
     * @param cmax the cmax to set
     */
    public void setCmax(int cmax) {
        this.cmax = cmax;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the offer
     */
    public ComInfo getOffer() {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(ComInfo offer) {
        this.offer = offer;
    }
}
