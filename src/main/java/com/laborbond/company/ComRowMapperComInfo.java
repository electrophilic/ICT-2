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
        u.setId(rs.getInt("com_id"));
        u.setName(rs.getString("com_name"));
        u.setPic(rs.getString("com_photo"));
        u.setInfo(rs.getString("com_info"));
        u.setNo(rs.getString("com_no"));
        u.setWeb(rs.getString("com_web"));
        u.setInfo(rs.getString("com_info"));
        u.setSize(rs.getString("com_size"));
        u.setU_fname(rs.getString("com_u_fname"));
        u.setU_lname(rs.getString("com_u_lname"));
        u.setEmail(rs.getString("com_email"));
        u.setTel(rs.getString("com_tel"));
        u.setAddress(rs.getString("com_address"));
        u.setLati(rs.getDouble("com_lati"));
        u.setLongi(rs.getDouble("com_longi"));
        u.setIndustry(rs.getString("com_industry"));
    
        return u;

    }
}
