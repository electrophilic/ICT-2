/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import com.laborbond.company.ComInfo;
import com.laborbond.company.CompanyService;
import static com.laborbond.controller.UploadHelper.fileUpload;
import com.laborbond.job.Job;
import com.laborbond.job.JobService;
import java.io.File;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hy
 */
@Controller
public class EmployerController {
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    JobService jobService;
    
    @RequestMapping(value = "/employer/update", method = RequestMethod.POST)//add post when deployed
    public String empInfo(HttpSession session,
            @RequestParam(value = "com-name", required = false) String name,
            @RequestParam(value = "com-add", required = false) String address,
            @RequestParam(value = "com-contact-number", required = false) String tel,
            @RequestParam(value = "com-email", required = false) String companyEmail,
            @RequestParam(value = "com-reg-no", required = false) String regNo,
            @RequestParam(value = "com-web", required = false) String web,
            @RequestParam(value = "com-size", required = false) String size,
            @RequestParam(value = "com-info", required = false) String info,
            @RequestParam(value = "com-ind", required = false) String industry,
            @RequestParam(value = "com-pic-prv", required = false) String prvPhoto,
            @RequestParam("photo") MultipartFile photo,
            ModelMap model){
        String type = (String) session.getAttribute("type");
        if (type != null) {
            ComInfo c = new ComInfo();
            c.Id = (Integer) session.getAttribute("id");
            c.name = name;
            c.no = regNo;
            c.pic = photo.getOriginalFilename();
            c.web = web;
            c.info = info;
            c.size = size;
            c.u_fname = "";
            c.u_lname = "";
            c.email = companyEmail;
            c.tel = tel;
            c.address = address;
            c.lati = 0;
            c.longi = 0;
            c.industry = industry;
            String rootPath = session.getServletContext().getRealPath("/resources/");
            File dir = new File(rootPath + File.separator + "uploadFiles" + File.separator + "employer" + File.separator + c.Id);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                fileUpload(photo, c.pic, dir);
            } catch (Exception ex) {
                c.pic=prvPhoto;
            }
            companyService.updateComInfo(c);
            return "redirect:/dash";
        }

        return "timeout";

    }
    
    @RequestMapping(value = "/employer/{id}")
    public String emp(HttpSession session, @PathVariable("id") int id, ModelMap model) {
        ComInfo c = companyService.getComInfo(id);
        map(model,c);
        return "company";
    }
    
    @RequestMapping(value = "/empd")
    public String empd(HttpSession session, ModelMap model) throws JSONException {
        Integer id = (Integer) session.getAttribute("id");
        ComInfo c = companyService.getComInfo(id);
        map(model,c);
        List<Job> jobs = jobService.getJobByComId(id);
        model.addAttribute("jobs", jobs);
        return "account-company";
    }
    
    private void map(ModelMap model, ComInfo c){
        model.addAttribute("id", c.Id);
        model.addAttribute("name", c.name);
        model.addAttribute("no", c.no);
        model.addAttribute("pic", c.getPicAddr());
        model.addAttribute("picprv", c.pic);
        model.addAttribute("web", c.web);
        model.addAttribute("info", c.info);
        model.addAttribute("size", c.size);
        model.addAttribute("cemail", c.email);
        model.addAttribute("tel", c.tel);
        model.addAttribute("address", c.address);
        model.addAttribute("lati", c.lati);
        model.addAttribute("longi", c.longi);
        model.addAttribute("industry", c.industry);
    }
    
    @RequestMapping(value = "/employer/deletejob", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpSession session, @RequestParam(value = "id") Integer jobId) {
        String accountType = (String) session.getAttribute("type");
        if (jobId != null&&accountType != null && "company".equals(accountType)) {
            jobService.comDeleteJob(jobId);
            return "OK";
        }
        return "INV";
    }
}
