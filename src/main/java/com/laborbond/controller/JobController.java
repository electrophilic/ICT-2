/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import com.laborbond.employee.EmInfo;
import com.laborbond.job.Job;
import com.laborbond.job.JobSearch;
import com.laborbond.job.JobService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hy
 */
@Controller
public class JobController {

    @Autowired
    JobService jobService;

    @RequestMapping(value = "/job/post", method = RequestMethod.GET)
    public String postJob(ModelMap model) {
        model.addAttribute("action","/job/add");
        model.addAttribute("id", 0);
        model.addAttribute("location", "");
        model.addAttribute("title", "");
        model.addAttribute("info", "");
        model.addAttribute("require", "");
        model.addAttribute("duty", "");
        model.addAttribute("apply", "");
        model.addAttribute("industry", "");
        model.addAttribute("cmin", 0);
        model.addAttribute("cmax", 0);
        model.addAttribute("type", "");
        return "post-a-job";
    }
    
    @RequestMapping(value = "/job/applyer/{id}", method = RequestMethod.GET)
    public String jobApplyer(ModelMap model, @PathVariable("id") Integer jobId) {
        List<EmInfo> EmInfo = jobService.getEmByJobId(jobId);
        model.addAttribute("applyerlist",EmInfo);
        return "view-app";
    }

    @RequestMapping(value = "/job/add", method = RequestMethod.POST)//add post when deployed
    public String postJob(HttpSession session, @RequestParam(value = "job-title") String title, @RequestParam(value = "job-location", required = false) String location, @RequestParam(value = "job-type", required = false) String type, @RequestParam(value = "job-industry", required = false) String industry, @RequestParam(value = "job-salary", required = false) int salary, @RequestParam(value = "responsibilities", required = false) String responsible, @RequestParam(value = "requirements", required = false) String requirement, @RequestParam(value = "other_info", required = false) String otherInfo, @RequestParam(value = "apply", required = false) String apply) throws JSONException {
        String accountType = (String) session.getAttribute("type");
        Integer comId=(Integer) session.getAttribute("id");
        if (accountType != null && "company".equals(accountType)) {
            Job job = new Job();
            job.offer.setId(comId);
            job.title = title;
            job.info = otherInfo;
            job.location = location;
            job.request = requirement;
            job.respon = responsible;
            job.apply = apply;
            job.industry = industry;
            job.cmin = 0;
            job.cmax = salary;
            job.type = type;
            jobService.postJob(job);
            return "redirect:/dash#job";
        } else {
            return "timeout";
        }
    }
    
    @RequestMapping(value = "/job/update", method = RequestMethod.POST)//add post when deployed
    public String updateJob(HttpSession session,
            @RequestParam(value = "job-title") String title,
            @RequestParam(value = "job-location", required = false) String location,
            @RequestParam(value = "job-type", required = false) String type,
            @RequestParam(value = "job-industry", required = false) String industry,
            @RequestParam(value = "job-salary", required = false) int salary,
            @RequestParam(value = "responsibilities", required = false) String responsible,
            @RequestParam(value = "requirements", required = false) String requirement,
            @RequestParam(value = "other_info", required = false) String otherInfo,
            @RequestParam(value = "apply", required = false) String apply,
            @RequestParam(value = "job-id") Integer jobId){
        String accountType = (String) session.getAttribute("type");
        if (accountType != null && "company".equals(accountType)) {
            
            int comId=(Integer) session.getAttribute("id");
            Job job = new Job();
            job.id = jobId;
            job.offer.setId(comId);
            job.title = title;
            job.info = otherInfo;
            job.location = location;
            job.request = requirement;
            job.respon = responsible;
            job.apply = apply;
            job.industry = industry;
            job.cmin = 0;
            job.cmax = salary;
            job.type = type;
            jobService.updateJob(job);
            return "redirect:/job/"+jobId;
        } else {
            return "timeout";
        }
        
    }
    
    @RequestMapping(value = "/job/{id}")//change this to restful url after refactoring
    public String viewJob(ModelMap model, @PathVariable("id") int id) {
        Job job = jobService.getJob(id);
        model.addAttribute("id", id);
        model.addAttribute("location", job.location);
        model.addAttribute("title", job.title);
        model.addAttribute("info", job.info);
        model.addAttribute("require", job.request);
        model.addAttribute("duty", job.respon);
        model.addAttribute("apply", job.apply);
        model.addAttribute("industry", job.industry);
        model.addAttribute("cmin", job.cmin);
        model.addAttribute("cmax", job.cmax);
        model.addAttribute("type", job.type);
        model.addAttribute("compic", job.offer.getPicAddr());
        model.addAttribute("cominfo", job.offer.getInfo());
        model.addAttribute("comid", job.offer.getId());
        model.addAttribute("comno", job.offer.getNo());
        model.addAttribute("comind", job.offer.getIndustry());
        model.addAttribute("comsize", job.offer.getSize());
        model.addAttribute("comweb", job.offer.getWeb());
        return "job-details";
    }
    

    @RequestMapping(value = "/job/{id}/edit")//change this to restful url after refactoring
    public String jobEdit(ModelMap model, @PathVariable("id") int id) {
        Job job = jobService.getJob(id);
        model.addAttribute("action","/job/update");
        model.addAttribute("id", id);
        model.addAttribute("location", job.location);
        model.addAttribute("title", job.title);
        model.addAttribute("info", job.info);
        model.addAttribute("require", job.request);
        model.addAttribute("duty", job.respon);
        model.addAttribute("apply", job.apply);
        model.addAttribute("industry", job.industry);
        model.addAttribute("cmin", job.cmin);
        model.addAttribute("cmax", job.cmax);
        model.addAttribute("type", job.type);
        return "post-a-job";
    }
    @RequestMapping(value = "/find/job")
    public String search(ModelMap model,
            @RequestParam(value = "s", required = false, defaultValue = "") String key,
            @RequestParam(value = "ind", required = false) String[] ind,
            @RequestParam(value = "type", required = false) String[] type,
            @RequestParam(value = "loc", required = false) String[] loc,
            @RequestParam(value = "cmin", required = false) Integer cmin,
            @RequestParam(value = "cmax", required = false) Integer cmax) {
        String[] empty = {""};
        ind = ind == null ? empty : ind;
        type = type == null ? empty : type;
        loc = loc == null ? empty : loc;
        JobSearch u = new JobSearch();
        u.text = key;
        u.cmin = (cmin != null) ? cmin : 0;
        u.cmax = (cmax != null) ? cmax : Integer.MAX_VALUE;
        u.industry = ind;
        u.type = type;
        u.location = loc;
        
        List<Job> jobs = jobService.search(u);
        model.addAttribute("s", key);
        model.addAttribute("res", jobs);
        model.addAttribute("count", jobs.size());
        return "find-jobs";
    }
}
