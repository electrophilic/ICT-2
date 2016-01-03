/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class SkillRowMapper implements RowMapper<Skill> {

    @Override
    public Skill mapRow(ResultSet rs,int i) throws SQLException {  
        Skill u=new Skill();
        u.skillId=rs.getInt("skill_id");
        u.skillInfo = rs.getString("skill_info");
        return u;

    }
}
