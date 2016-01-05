/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import com.laborbond.employee.EmEdu;
import com.laborbond.employee.EmExp;
import com.laborbond.employee.EmInfo;
import com.laborbond.employee.EmSearch;
import com.laborbond.employee.EmployeeService;
import com.laborbond.job.Job;
import com.laborbond.job.JobService;
import com.laborbond.skill.Skill;
import com.laborbond.skill.SkillService;
import com.laborbond.user.User;
import com.laborbond.user.UserService;
import static com.laborbond.controller.UploadHelper.fileUpload;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
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
public class ApplyerController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    @Autowired
    SkillService skillService;

    @Autowired
    JobService jobService;

    @RequestMapping(value = "/resume/post", method = RequestMethod.GET)
    public String postJob(ModelMap model) {
        return "post-a-resume";
    }

    //emInfo.pic,emInfo.birth,emInfo.tel,emInfo.info,emInfo.skill,emInfo.fname,emInfo.lname,emInfo.title,emInfo.industry,emInfo.location,emInfo.doc,emInfo.Id
    @RequestMapping(value = "/employee/update", method = RequestMethod.POST)//add post when deployed
    public String postResume(HttpSession session,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "contact_number") String contactNumber,
            @RequestParam(value = "about_me") String about,
            @RequestParam(value = "my_skill") String skill,
            @RequestParam(value = "key_skills", required = false) String keySkills,
            @RequestParam(value = "industry") String industry,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("resume-file") MultipartFile resumeFile,
            @RequestParam(value = "school", required = false) String[] schools,
            @RequestParam(value = "education-dates", required = false) String[] educationDates,
            @RequestParam(value = "qualifications", required = false) String[] qualifications,
            @RequestParam(value = "notes", required = false) String[] notes,
            @RequestParam(value = "employer", required = false) String[] employers,
            @RequestParam(value = "experience-dates", required = false) String[] experienceDates,
            @RequestParam(value = "job-title", required = false) String[] jobTitles,
            @RequestParam(value = "responsibilities", required = false) String[] responsibilities,
            @RequestParam(value = "resume-pic-prv", required = false) String prvPhoto,
            @RequestParam(value = "resume-doc-prv", required = false) String prvDoc,
            @RequestParam(value = "resume-dob-prv", required = false) Long prvDob,
            @RequestParam(value = "dobday") Integer dobDay,
            @RequestParam(value = "dobmonth") Integer dobMonth,
            @RequestParam(value = "dobyear") Integer dobYear) {

        String accountType = (String) session.getAttribute("type");
        // we need to use either person or employee, do not use both of them
        if (accountType != null && "employee".equals(accountType)) {
            EmInfo emInfo = new EmInfo();
            emInfo.time=System.currentTimeMillis();
            emInfo.setPic(photo.getOriginalFilename());
            if(dobDay != null&&dobMonth != null&&dobYear != null){
                Calendar dob = Calendar.getInstance();
                dob.set(dobYear, dobMonth - 1/*in java, month also start with 0*/, dobDay);
                emInfo.setBirth(dob.getTimeInMillis());
            }else{
                emInfo.setBirth(prvDob==null?0:prvDob);
            }
            emInfo.setTel(contactNumber);
            emInfo.setInfo(about);
            emInfo.setSkill(skill);
            emInfo.setTitle(title);
            emInfo.setIndustry(industry);
            emInfo.setLocation(location);
            emInfo.setDoc(resumeFile.getOriginalFilename());
            emInfo.setId((int) (Integer) session.getAttribute("id"));
            String rootPath = session.getServletContext().getRealPath("/resources/");
            File dir = new File(rootPath + File.separator + "uploadFiles" + File.separator + "resume" + File.separator + emInfo.getId());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                fileUpload(photo, emInfo.getPic(), dir);
            } catch (Exception ex) {
                emInfo.pic=prvPhoto==null?"":prvPhoto;
            }
            try {
                fileUpload(resumeFile, emInfo.getDoc(), dir);
            } catch (Exception ex) {
                emInfo.doc=prvDoc==null?"":prvDoc;
            }
            employeeService.updateEmInfo(emInfo);
            Map<String, Skill> skillMap = getSkillMap();
            if (keySkills != null) {
                List<Skill> skillList = new ArrayList<>();
                String[] keySkillStringList = keySkills.split(", ");
                for (String keySkill : keySkillStringList) {
                    Skill e = skillMap.get(keySkill);
                    if (e == null) {
                        skillService.addNewSkill(keySkill);
                        skillMap = getSkillMap();
                        e = skillMap.get(keySkill);
                    }
                    if (e != null) {
                        skillList.add(e);
                    }
                }
                skillService.addSkill(emInfo.Id, skillList, "employee");
            }
            
            employeeService.clearEmExp(emInfo.getId());
            employeeService.clearEmEdu(emInfo.getId());
            if (schools != null) {
                for (int i = 0; i < schools.length; i++) {
                    if (!schools[i].equals("")) {
                        EmEdu emEdu = new EmEdu();
                        emEdu.setEmId(emInfo.getId());
                        emEdu.setAcademy(schools[i]);
                        emEdu.setMajor(i < qualifications.length ? qualifications[i] : "");
                        emEdu.setYear(i < educationDates.length ? educationDates[i] : "");
                        emEdu.setInfo(i < notes.length ? notes[i] : "");
                        employeeService.addEmEdu(emEdu);
                    }
                }
            }
            if (employers != null) {
                for (int i = 0; i < employers.length; i++) {
                    if (!employers[i].equals("")) {
                        EmExp emExp = new EmExp();
                        emExp.setEmId(emInfo.getId());
                        emExp.setComName(employers[i]);
                        emExp.setPeriod(i < experienceDates.length ? experienceDates[i] : "");
                        emExp.setRespon(i < responsibilities.length ? responsibilities[i] : "");
                        emExp.setTitle(i < jobTitles.length ? jobTitles[i] : "");
                        employeeService.addEmExp(emExp);
                    }

                }
            }

            return "redirect:/resume/" + emInfo.getId();
        } else {
            return "timeout";
        }
    }

    @RequestMapping(value = "/resume/{id}")//change this to restful url after refactoring
    public String viewJob(ModelMap model, @PathVariable("id") int id) {
        EmInfo emInfo = employeeService.getEmInfo(id);
        User user = userService.getUser(id, "employee");
        List<EmExp> emExp = employeeService.getEmExp(id);
        List<EmEdu> emEdu = employeeService.getEmEdu(id);
        List<Skill> keySkill = skillService.getSkill(id, "employee");
        model.addAttribute("id", user.Id);
        model.addAttribute("fullname", String.format("%s %s", emInfo.getFname(), emInfo.getLname()));
        model.addAttribute("title", emInfo.getTitle());
        model.addAttribute("email", user.email);
        if (emInfo.pic != null&&!"".equals(emInfo.pic)) {
            model.addAttribute("pic", emInfo.getPicAddr());
        }
        if (emInfo.doc != null&&!"".equals(emInfo.doc)) {
            model.addAttribute("doc", emInfo.getDocAddr());
        }
        model.addAttribute("about", emInfo.getInfo());
        model.addAttribute("skill", emInfo.getSkill());
        model.addAttribute("keyskill", keySkill);
        model.addAttribute("location", emInfo.getLocation());
        model.addAttribute("industry", emInfo.getIndustry());
        model.addAttribute("phone", emInfo.getTel());
        model.addAttribute("dob", emInfo.getBirth());
        model.addAttribute("experience", emExp);
        model.addAttribute("education", emEdu);
        try {
            model.addAttribute("url", URLEncoder.encode("" + id, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ApplyerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "resume";
    }

    private Map<String, Skill> getSkillMap() {
        List<Skill> allSkill = skillService.getAllSkill();
        Map<String, Skill> skillMap = new HashMap<>();
        for (Skill s : allSkill) {
            skillMap.put(s.skillInfo, s);
        }
        return skillMap;
    }

    @RequestMapping(value = "/apyd")
    public String apyd(HttpSession session, ModelMap model) {

        Integer id = (Integer) session.getAttribute("id");

        EmInfo emInfo = employeeService.getEmInfo(id);
        List<EmExp> emExp = employeeService.getEmExp(id);
        List<EmEdu> emEdu = employeeService.getEmEdu(id);
        List<Skill> keySkill = skillService.getSkill(id, "employee");
        List<Job> jobs = jobService.getJobByEmId(id);
        model.addAttribute("fullname", String.format("%s %s", emInfo.getFname(), emInfo.getLname()));
        model.addAttribute("title", emInfo.getTitle());
        model.addAttribute("pic", emInfo.getPic());
        model.addAttribute("doc", emInfo.getDoc());
        model.addAttribute("about", emInfo.getInfo());
        model.addAttribute("skill", emInfo.getSkill());
        model.addAttribute("location", emInfo.getLocation());
        model.addAttribute("industry", emInfo.getIndustry());
        model.addAttribute("phone", emInfo.getTel());
        model.addAttribute("dob", Long.toString(emInfo.birth));

        model.addAttribute("keyskill", keySkill);
        model.addAttribute("experience", emExp);
        model.addAttribute("education", emEdu);
        model.addAttribute("jobs", jobs);
        try {
            model.addAttribute("url", URLEncoder.encode("" + id, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ApplyerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "account-personal";

    }

    @RequestMapping(value = "/find/candidates", method = RequestMethod.GET)
    public String search(
            @RequestParam(value = "s", required = false) String key,
            @RequestParam(value = "ind", required = false) String[] ind,
            @RequestParam(value = "date", required = false) Long date,
            @RequestParam(value = "loc", required = false) String[] loc,
            ModelMap model
    ) {
        String[] empty = {""};
        ind = ind == null ? empty : ind;
        loc = loc == null ? empty : loc;
        key = key == null ? "" : key;
        date= date == null ? -1 : date;
        
        model.addAttribute("ind", Arrays.asList(ind));
        model.addAttribute("date", date);
        model.addAttribute("loc", Arrays.asList(loc));
        
        EmSearch k = new EmSearch();
        k.text = key;
        k.industry = ind.clone();
        k.location = loc.clone();
        k.date = date == -1?Double.MIN_EXPONENT:(System.currentTimeMillis()-86400000l*date);
        List<EmInfo> em = employeeService.search(k);
        model.addAttribute("s", key);
        model.addAttribute("res", em);
        model.addAttribute("count", em.size());
        return "find-candidates";
    }

    @RequestMapping(value = "/job/apply", method = RequestMethod.GET)
    public String apply(@RequestParam(value = "jobId", required = false) Integer jobId,
            HttpSession session,
            ModelMap model) {
        String accountType = (String) session.getAttribute("type");
        if (jobId == null) {
            return "redirect:/";
        }
        if (accountType != null && "employee".equals(accountType)) {
            int emId = (Integer) session.getAttribute("id");
            jobService.applyJob(jobId, emId);
            return "redirect:/dash#";
        }
        model.addAttribute("applyerr", "Please login!");
        return "forward:/job/" + jobId;
    }

    @RequestMapping(value = "/employee/deletejob", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpSession session, @RequestParam(value = "id") Integer jobId) {
        String accountType = (String) session.getAttribute("type");
        if (jobId != null && accountType != null && "employee".equals(accountType)) {
            int emId = (Integer) session.getAttribute("id");
            jobService.emDeleteJob(jobId, emId);
            return "OK";
        }
        return "INV";
    }

}
