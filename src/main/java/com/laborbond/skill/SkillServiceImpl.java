/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.skill;

import com.laborbond.employee.EmExp;
import com.laborbond.employee.EmRowMapperEmExp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Fu
 */
public class SkillServiceImpl implements SkillService{

    @Autowired
    public DataSource dataSource;
    
    @Override
    public void addSkill(int id, List<Skill> skill, String type) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        String sql ="";
        if(type.equals("company")){
            sql = "DELETE from `job_skill` WHERE `job_id`= ?";
            jdbcTemplate.update(sql, new Object[] {id});
            
            for(Skill e:skill){
            sql = "INSERT INTO `job_skill` (`job_id`, `skill_id`) VALUES(?,?)";
            jdbcTemplate.update(sql, new Object[] { id,e.skillId}); 
            }
            
        }else{
            sql = "DELETE from `employee_skill` WHERE `em_id`= ?";
            jdbcTemplate.update(sql, new Object[] {id});
            
            for(Skill e:skill){
            sql = "INSERT INTO `employee_skill` (`em_id`, `skill_id`) VALUES(?,?)";
            jdbcTemplate.update(sql, new Object[] { id,e.skillId}); 
            }
        }
    }

    @Override
    public void addNewSkill(String skill) {
        String sql = "INSERT INTO `allskill` (`skill_info`) VALUES(?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { skill});
        
    }

    @Override
    public List<Skill> getAllSkill() {
        List<Skill> skill = new ArrayList<Skill>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        String sql = "select * from allskill";
        skill = jdbcTemplate.query(sql,new Object[]{}, new SkillRowMapper());
        return skill;
    }

    @Override
    public List<Skill> getSkill(int id, String type) {
        List<Skill> skill = new ArrayList<Skill>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        if(type.equals("company")){
            String sql = "select * from `job_skill`, `allskill` WHERE `job_skill`.`skill_id` = `allskill`.`skill_id` AND`job_id`= ?";
            skill = jdbcTemplate.query(sql,new Object[]{id}, new SkillRowMapper());
            return skill;
        }else{
            String sql = "select * from `employee_skill`, `allskill` WHERE `employee_skill`.`skill_id` = `allskill`.`skill_id` AND`em_id`= ?";
            skill = jdbcTemplate.query(sql,new Object[]{id}, new SkillRowMapper());
            return skill;
        }
    }
    
}
