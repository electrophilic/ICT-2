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
    public int Id ;
    public String name;
    public String no;
    public String pic;
    public String web;
    public String info;
    public String size;
    public String u_fname;
    public String u_lname;
    public String email;
    public String tel;
    public String address;
    public double lati;
    public double longi;
    public String industry;
    



    public ComInfo() {
        
    }
    
    public String getPicAddr() {
        return (pic != null && !"".equals(pic)) ? "/resources/uploadFiles/employer/" + Id + "/" + pic : "/resources/images/logo.png";
    }

    
    public boolean isValid(){
        return Id!=-1;
    }
}
