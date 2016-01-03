package com.laborbond.user;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fu
 */
public class User {
    public int Id ;
    public String email ;
    public String type;
    public String fname;
    public String lname;
    public String comname;



    public User() {
        
    }

    
    public boolean isValid(){
        return Id!=-1;
    }
    public User(int Id, String email,String type, String fname, String lname) {
        this.Id = Id;
        this.email = email;
        this.type = type;
        this.fname = fname;
        this.lname = lname;
        
    }

    public int getId() {
        return Id;
    }

    public String getEmail() {
        return email;
    }
    
    public String getType() {
        return type;
    }
    
    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
            
}
