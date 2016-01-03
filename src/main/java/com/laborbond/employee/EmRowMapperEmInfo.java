/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class EmRowMapperEmInfo implements RowMapper<EmInfo> {

    @Override
    public EmInfo mapRow(ResultSet rs,int i) throws SQLException {  
        EmInfo u=new EmInfo();
        u.Id=rs.getInt("em_id");
        u.pic=rs.getString("em_pic");
        u.birth=rs.getLong("em_birth");
        u.tel=rs.getString("em_tel");
        u.info=rs.getString("em_info");
        u.skill = rs.getString("em_skill");   
        u.fname=rs.getString("em_fname");
        u.lname = rs.getString("em_lname");
        u.title=rs.getString("em_title");
        u.industry=rs.getString("em_industry");
        u.location=rs.getString("em_location");
        u.doc=rs.getString("em_doc");
        u.time = rs.getLong("em_time");
        return u;


    }
}
