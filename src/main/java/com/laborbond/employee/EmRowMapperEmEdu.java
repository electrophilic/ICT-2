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
public class EmRowMapperEmEdu implements RowMapper<EmEdu> {

    @Override
    public EmEdu mapRow(ResultSet rs,int i) throws SQLException {  
        EmEdu u=new EmEdu();
        u.Id=rs.getInt("edu_id");
        u.emId=rs.getInt("em_id");
        u.academy=rs.getString("em_academy");
        u.major=rs.getString("em_major");
        u.year=rs.getString("em_year");
        u.info=rs.getString("em_info");
        return u;
        

    }
}