/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.job;

import com.laborbond.employee.EmInfo;
import com.laborbond.employee.EmRowMapperEmInfo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Fu
 */
public class JobServiceImpl implements JobService{
    @Autowired
    public DataSource dataSource;
    
    @Override
    public List<Job> getJob() {
        List<Job> job = new ArrayList<Job>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        String sql = "select * from job_id, job_filter,company_info where job_id.job_id= job_filter.job_id AND company_info.com_id = job_id.com_id";
        job = jdbcTemplate.query(sql,new Object[]{}, new JobRowMapperJob());
        return job;
    }
    
    @Override
    public Job getJob(int id) {
        Job job = new Job();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        String sql = "select * from job_id, job_filter,company_info where company_info.com_id = job_id.com_id AND job_id.job_id= job_filter.job_id and job_id.job_id = ?";
        job = jdbcTemplate.query(sql,new Object[]{id}, new JobRowMapperJob()).get(0);
        return job;
    }

    @Override
    public void postJob(Job job) {
        String sql = "INSERT INTO `job_id` (`com_id`,`job_title`, `job_info`, `job_location`, `job_request`, `job_respon`, `job_apply`, `job_industry`, `job_time`) VALUES(?,?,?,?,?,?,?,?,?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { job.offer.Id,job.title,job.info,job.location,job.request,job.respon,job.apply,job.industry,job.time});
        
        sql = "INSERT INTO `job_filter` (`job_cmin`, `job_cmax`,`job_type`) VALUES(?,?,?)";
        jdbcTemplate.update(sql, new Object[] { job.cmin,job.cmax,job.type});
    }

    @Override
    public boolean applyJob(int job_id, int em_id) {
        boolean check = checkApply(em_id,job_id);
        if(check){
        String sql = "INSERT INTO `job_apply` (`em_id`,`job_id`) VALUES(?,?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { em_id,job_id});
        return true;
        }else{
            return false;
        }
    }
    
    public boolean checkApply(int em_id, int job_id) {
       List<Job> job = new ArrayList<Job>();
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       String sql = "SELECT * FROM `job_apply` , `job_id`, `job_filter`,`company_info` WHERE `company_info`.`com_id` = `job_id`.`com_id` AND `job_apply`.`job_id` = `job_id`.`job_id` AND `job_filter`.`job_id` = `job_id`.`job_id` AND `job_apply`.`em_id` = ? AND `job_apply`.`job_id` =?";
       job =  jdbcTemplate.query(sql,new Object[]{em_id,job_id}, new JobRowMapperJob());
       if (job.isEmpty()){
           return true;
       }else{
           return false;
       }
    }
    
    @Override
    public List<EmInfo> getEmByJobId(int job_id) {
       List<EmInfo> em = new ArrayList<EmInfo>();
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       String sql = "SELECT * FROM `job_apply` , `employee_info` WHERE `job_apply`.`em_id` = `employee_info`.`em_id` AND `job_apply`.`job_id` = ?";
       em =  jdbcTemplate.query(sql,new Object[]{job_id}, new EmRowMapperEmInfo());
       return em;
    }

    @Override
    public List<Job> getJobByEmId(int em_id) {
       List<Job> job = new ArrayList<Job>();
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       String sql = "SELECT * FROM `job_apply` , `job_id`, `job_filter`,`company_info` WHERE `company_info`.`com_id` = `job_id`.`com_id` AND `job_apply`.`job_id` = `job_id`.`job_id` AND `job_filter`.`job_id` = `job_id`.`job_id` AND `job_apply`.`em_id` = ?";
       job =  jdbcTemplate.query(sql,new Object[]{em_id}, new JobRowMapperJob());
       return job;
    }

    @Override
    public List<Job> getJobByComId(int com_id) {
       List<Job> job = new ArrayList<Job>();
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       String sql = "SELECT * FROM `job_id`, `job_filter`,`company_info` WHERE `company_info`.`com_id` = `job_id`.`com_id` AND `job_filter`.`job_id` = `job_id`.`job_id` AND `job_id`.`com_id` = ?";
       job =  jdbcTemplate.query(sql,new Object[]{com_id}, new JobRowMapperJob());
       return job;
    }

    @Override
    public void emDeleteJob(int job_id, int em_id) {
        String sql = "DELETE FROM `job_apply` WHERE `em_id` = ? AND `job_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] {em_id,job_id});
    }

    @Override
    public void comDeleteJob(int job_id) {
        String sql = "Update `job_id` SET `job_state`=1 WHERE `job_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { job_id});
    }
    

   
    @Override
    public List<Job> search(JobSearch jobs) {
        //public String search(JobSearch jobs) {   
        List<Job> job = new ArrayList<Job>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM `job_id`, `job_filter`,`company_info` WHERE `company_info`.`com_id` = `job_id`.`com_id` AND ( `job_id`.`job_id`= `job_filter`.`job_id` AND `job_id`.`job_state` = 0 ";

        Object[] text = jobs.text.split(" ");
        for (int n = 0; n < text.length; n++) {
            text[n] = "%" + text[n] + "%";
            sql = sql + "AND LOWER(`job_id`.`job_title`) LIKE LOWER (?)";
        }

        sql = sql + ") AND `job_filter`.`job_cmin` >= ? AND `job_filter`.`job_cmax` <= ?";

        sql = sql + " AND ( 1=0";
        String[] type = jobs.type;
        for (int n = 0; n < type.length; n++) {
            type[n] = "%" + type[n] + "%";
            sql = sql + " OR LOWER (`job_filter`.`job_type`) LIKE LOWER (?)";
        }

        sql = sql + ") AND ( 1=0";
        String[] location = jobs.location;
        for (int n = 0; n < location.length; n++) {
            type[n] = "%" + location[n] + "%";
            sql = sql + " OR LOWER(`job_id`.`job_location`) LIKE LOWER(?)";
        }

        sql = sql + ") AND ( 1=0";
        String[] industry = jobs.industry;
        for (int n = 0; n < industry.length; n++) {
            industry[n] = "%" + industry[n] + "%";
            sql = sql + " OR LOWER(`job_id`.`job_industry`) LIKE LOWER(?)";
        }
        sql = sql + ")";
        Object[] c = {jobs.cmin, jobs.cmax};
        ArrayList param = new ArrayList();
        param.addAll(Arrays.asList(text));
        param.addAll(Arrays.asList(c));
        param.addAll(Arrays.asList(type));
        param.addAll(Arrays.asList(location));
        param.addAll(Arrays.asList(industry));

        job = jdbcTemplate.query(sql, param.toArray(), new JobRowMapperJob());

        //return sql;
        return job;
    }

    @Override
    public void updateJob(Job job) {
        String sql = "Update `job_id` SET `job_title`=?,`job_info` = ?,`job_location`=?,`job_request`=?,`job_respon`=?,`job_apply`=?,`job_industry`=?,`job_time`=? WHERE `job_id` = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[]{job.title, job.info, job.location, job.request, job.respon, job.apply, job.industry, job.time,  job.id});
        
        sql = "Update `job_filter` SET `job_cmin`=?,`job_cmax` = ?,`job_type`=? WHERE `job_id` = ?";
        jdbcTemplate.update(sql, new Object[]{job.cmin, job.cmax, job.type, job.id});
    
    }
}
