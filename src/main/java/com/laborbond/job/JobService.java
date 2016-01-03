/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.job;

import com.laborbond.employee.EmInfo;
import java.util.List;

/**
 *
 * @author Fu
 */
public interface JobService {
    
    public List<Job> getJob();
    
    public Job getJob(int id);
        
    public void postJob(Job job);
    
    public void updateJob(Job job);
    
    public boolean applyJob(int job_id, int em_id);
    
    public boolean checkApply(int em_id, int job_id);
    
    public List<EmInfo> getEmByJobId(int job_id);
    
    public List<Job> getJobByEmId(int em_id);
    
    public List<Job> getJobByComId(int com_id);
    
    public void emDeleteJob(int job_id, int em_id);
    
    public void comDeleteJob(int job_id);
    
    public List<Job> search(JobSearch jobs);
    //public String search(JobSearch jobs);
            
}
