/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import com.laborbond.job.Job;
import com.laborbond.job.JobSearch;
import com.laborbond.job.JobService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    JobService jobService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        JobSearch u = new JobSearch();
        u.text = "";
        u.cmin = 3000;
        u.cmax = 6000;
        String[] empty = {""};
        u.industry = empty.clone();
        u.type = empty.clone();
        u.location = empty.clone();
        
        List<Job> jobs = jobService.search(u);
        
        model.addAttribute("res", jobs);
        return "home";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value = "/testimonials", method = RequestMethod.GET)
    public String feedback(ModelMap model) {
        return "testimonials";
    }

    @RequestMapping(value = "/realpath", method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpSession session, @RequestParam(value = "path") String path) {
        return session.getServletContext().getRealPath(path);
    }

}
