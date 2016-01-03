package com.laborbond.user;


import com.laborbond.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fu
 */
class UserRowMapperComId implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs,int i) throws SQLException {  
        User u=new User();
        u.Id=rs.getInt("com_id");
        u.email=rs.getString("com_email");
        u.type = "company";

        return u;
    }   
}
