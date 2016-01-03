/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.employee;

import com.laborbond.employee.EmExp;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class EmRowMapperEmExp implements RowMapper<EmExp> {

    @Override
    public EmExp mapRow(ResultSet rs,int i) throws SQLException {  
        EmExp u=new EmExp();
        u.Id=rs.getInt("exp_id");
        u.emId=rs.getInt("em_id");
        u.comName=rs.getString("com_name");
        u.respon=rs.getString("em_respon");
        u.title=rs.getString("em_title");
        u.period=rs.getString("em_period");
        return u;
        

    }
}
