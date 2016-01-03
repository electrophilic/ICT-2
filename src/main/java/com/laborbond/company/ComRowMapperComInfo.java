/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.company;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class ComRowMapperComInfo implements RowMapper<ComInfo> {

    @Override
    public ComInfo mapRow(ResultSet rs,int i) throws SQLException {  
        ComInfo u=new ComInfo();
        u.Id=rs.getInt("com_id");
        u.name=rs.getString("com_name");
        u.pic=rs.getString("com_photo");
        u.info=rs.getString("com_info");
        u.no=rs.getString("com_no");
        u.web = rs.getString("com_web");
        u.info = rs.getString("com_info");
        u.size = rs.getString("com_size");
        u.u_fname = rs.getString("com_u_fname");
        u.u_lname = rs.getString("com_u_lname");
        u.email = rs.getString("com_email");
        u.tel = rs.getString("com_tel");
        u.address = rs.getString("com_address");
        u.lati = rs.getDouble("com_lati");
        u.longi = rs.getDouble("com_longi");
        u.industry = rs.getString("com_industry");
    
        return u;

    }
}
