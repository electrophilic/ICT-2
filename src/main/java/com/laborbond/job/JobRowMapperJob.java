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
        u.offer.Id =rs.getInt("com_id");
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
        u.offer.name=rs.getString("com_name");
        u.offer.pic=rs.getString("com_photo");
        u.offer.info=rs.getString("com_info");
        u.offer.no=rs.getString("com_no");
        u.offer.web = rs.getString("com_web");
        u.offer.size = rs.getString("com_size");
        u.offer.u_fname = rs.getString("com_u_fname");
        u.offer.u_lname = rs.getString("com_u_lname");
        u.offer.email = rs.getString("com_email");
        u.offer.tel = rs.getString("com_tel");
        u.offer.address = rs.getString("com_address");
        u.offer.lati = rs.getDouble("com_lati");
        u.offer.longi = rs.getDouble("com_longi");
        u.offer.industry = rs.getString("com_industry");
        return u;

    }
}