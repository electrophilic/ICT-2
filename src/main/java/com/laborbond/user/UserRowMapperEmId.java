/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.user;

import com.laborbond.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class UserRowMapperEmId implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs,int i) throws SQLException {  
        User u=new User();
        u.Id=rs.getInt("em_id");
        u.email=rs.getString("em_email");
        u.type = "employee";
        return u;
    }
}
