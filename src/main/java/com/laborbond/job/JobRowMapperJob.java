/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.job;

import com.laborbond.company.ComInfo;
import com.laborbond.job.Job;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Fu
 */
public class JobRowMapperJob implements RowMapper<Job> {

    @Override
    public Job mapRow(ResultSet rs,int i) throws SQLException {  
        Job u=new Job();
        ComInfo offer=new ComInfo();
        u.setOffer(offer);
        u.id=rs.getInt("job_id");
        u.offer.setId(rs.getInt("com_id"));
        u.title = rs.getString("job_title");
        u.info= rs.getString("job_info");
        u.location = rs.getString("job_location");
        u.request = rs.getString("job_request");
        u.respon = rs.getString("job_respon");
        u.apply = rs.getString("job_apply");
        u.industry = rs.getString("job_industry");
        u.cmin =rs.getInt("job_cmin");
        u.cmax =rs.getInt("job_cmax");
        u.type = rs.getString("job_type");
        u.time = rs.getLong("job_time");
        u.state = rs.getInt("job_state");
        u.offer.setName(rs.getString("com_name"));
        u.offer.setPic(rs.getString("com_photo"));
        u.offer.setInfo(rs.getString("com_info"));
        u.offer.setNo(rs.getString("com_no"));
        u.offer.setWeb(rs.getString("com_web"));
        u.offer.setSize(rs.getString("com_size"));
        u.offer.setU_fname(rs.getString("com_u_fname"));
        u.offer.setU_lname(rs.getString("com_u_lname"));
        u.offer.setEmail(rs.getString("com_email"));
        u.offer.setTel(rs.getString("com_tel"));
        u.offer.setAddress(rs.getString("com_address"));
        u.offer.setLati(rs.getDouble("com_lati"));
        u.offer.setLongi(rs.getDouble("com_longi"));
        u.offer.setIndustry(rs.getString("com_industry"));
        return u;

    }
}