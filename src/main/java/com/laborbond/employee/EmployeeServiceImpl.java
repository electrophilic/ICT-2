//a/A
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Fu
 */
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public DataSource dataSource;

    @Override
    public void updateEmInfo(EmInfo emInfo) {
        String sql = "Update `employee_info` SET `em_pic`=?,`em_birth` = ?,`em_tel`=?,`em_info`=?,`em_skill`=?,`em_title`=?,`em_industry`=?,`em_location`=?,`em_doc`=?, `em_time`=? WHERE `em_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{emInfo.pic, emInfo.birth, emInfo.tel, emInfo.info, emInfo.skill, emInfo.title, emInfo.industry, emInfo.location, emInfo.doc, emInfo.time, emInfo.Id});

    }

    @Override
    public EmInfo getEmInfo(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM  `employee_info` WHERE `em_id` =  ? ";
        EmInfo emInfo = (EmInfo) jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmRowMapperEmInfo());
        return emInfo;
    }

    @Override
    public void addEmExp(EmExp emExp) {
        String sql = "INSERT INTO `employee_experience` (`em_id`, `com_name`, `em_period`, `em_respon`, `em_title`) VALUES(?,?,?,?,?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{emExp.emId, emExp.comName, emExp.period, emExp.respon, emExp.title});
    }

    @Override
    public void deleteEmExp(int exp_Id) {
        String sql = "DELETE FROM `employee_experience` WHERE `exp_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{exp_Id});
    }

    @Override
    public List<EmExp> getEmExp(int emId) {
        List<EmExp> exp = new ArrayList<EmExp>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from employee_experience where em_id= ?";
        exp = jdbcTemplate.query(sql, new Object[]{emId}, new EmRowMapperEmExp());
        return exp;
    }

    @Override
    public void addEmEdu(EmEdu emEdu) {
        String sql = "INSERT INTO `employee_education` (`em_id`, `em_academy`, `em_major`, `em_year`, `em_info`) VALUES(?,?,?,?,?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{emEdu.emId, emEdu.academy, emEdu.major, emEdu.year, emEdu.info});
    }

    @Override
    public void deleteEmEdu(int edu_Id) {
        String sql = "DELETE FROM `employee_education` WHERE `edu_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{edu_Id});
    }

    @Override
    public List<EmEdu> getEmEdu(int emId) {
        List<EmEdu> edu = new ArrayList<EmEdu>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from employee_education where em_id= ?";
        edu = jdbcTemplate.query(sql, new Object[]{emId}, new EmRowMapperEmEdu());
        return edu;
    }

    @Override
    public List<EmInfo> search(EmSearch emSearch) {
        List<EmInfo> emInfo = new ArrayList<EmInfo>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM `employee_info` WHERE ( 1=0 ";

        String[] text = emSearch.text.split(" ");
        for (int n = 0; n < text.length; n++) {
            text[n] = "%" + text[n] + "%";
            sql = sql + "OR LOWER(`em_title`) LIKE LOWER (?)";
        }

        sql = sql + ") AND ( 1=0";
        String[] location = emSearch.location;
        for (int n = 0; n < location.length; n++) {
            location[n] = "%" + location[n] + "%";
            sql = sql + " OR LOWER(`em_location`) LIKE LOWER(?)";
        }

        sql = sql + ") AND ( 1=0";
        String[] industry = emSearch.industry;
        for (int n = 0; n < industry.length; n++) {
            industry[n] = "%" + industry[n] + "%";
            sql = sql + " OR LOWER(`em_industry`) LIKE LOWER(?)";
        }
        sql = sql + ") AND ( `em_time`>=?)";
        ArrayList param = new ArrayList();
        param.addAll(Arrays.asList(text));
        param.addAll(Arrays.asList(location));
        param.addAll(Arrays.asList(industry));
        param.add(emSearch.date);
        emInfo = jdbcTemplate.query(sql, param.toArray(), new EmRowMapperEmInfo());

        return emInfo;
    }

    @Override
    public void clearEmExp(int emId) {
        String sql = "DELETE FROM `employee_experience` WHERE `em_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{emId});
    }

    @Override
    public void clearEmEdu(int emId) {
        String sql = "DELETE FROM `employee_education` WHERE `em_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{emId});
    }
}
