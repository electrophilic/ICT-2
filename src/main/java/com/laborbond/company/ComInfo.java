/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.company;

/**
 *
 * @author Fu
 */
public class ComInfo {//employee infomation
    private int Id ;
    private String name;
    private String no;
    private String pic;
    private String web;
    private String info;
    private String size;
    private String u_fname;
    private String u_lname;
    private String email;
    private String tel;
    private String address;
    private double lati;
    private double longi;
    private String industry;
    



    public ComInfo() {
        
    }
    
    public String getPicAddr() {
        return (pic != null && !"".equals(pic)) ? "/resources/uploadFiles/employer/" + Id + "/" + pic : "/resources/images/logo.png";
    }

    
    public boolean isValid(){
        return Id!=-1;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * @return the pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic the pic to set
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * @return the web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(String web) {
        this.web = web;
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
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the u_fname
     */
    public String getU_fname() {
        return u_fname;
    }

    /**
     * @param u_fname the u_fname to set
     */
    public void setU_fname(String u_fname) {
        this.u_fname = u_fname;
    }

    /**
     * @return the u_lname
     */
    public String getU_lname() {
        return u_lname;
    }

    /**
     * @param u_lname the u_lname to set
     */
    public void setU_lname(String u_lname) {
        this.u_lname = u_lname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the lati
     */
    public double getLati() {
        return lati;
    }

    /**
     * @param lati the lati to set
     */
    public void setLati(double lati) {
        this.lati = lati;
    }

    /**
     * @return the longi
     */
    public double getLongi() {
        return longi;
    }

    /**
     * @param longi the longi to set
     */
    public void setLongi(double longi) {
        this.longi = longi;
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
}
