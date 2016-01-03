/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import com.laborbond.user.User;
import com.laborbond.user.UserService;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author hy
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String login(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "pass", required = false) String passwd, HttpSession session
    ) throws JSONException {
        User user = new User();
        user.email = email;
        user = userService.login(user, passwd);
        JSONObject res = new JSONObject();
        if (user == null) {

            res.put("state", "mismatch");
            return res.toString();
        }
        res.put("state", "success");
        session.setAttribute("id", user.Id);
        session.setAttribute("type", user.type);
        res.put("id", user.Id);
        res.put("type", user.type);
        return res.toString();

    }

    @RequestMapping(value = "/info")//add post when deployed
    public @ResponseBody
    String info(HttpSession session) throws JSONException {
        JSONObject res = new JSONObject();
        String type = (String) session.getAttribute("type");
        if (type != null) {
            res.put("id", session.getAttribute("id"));
            res.put("type", session.getAttribute("type"));
        } else {
            res.put("id", -1);
            res.put("type", "visitor");
        }
        return res.toString();
    }

    @RequestMapping(value = "/logout")//add post when deployed
    public String logout(HttpSession session) throws JSONException {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/reg/{type}", method = RequestMethod.POST)
    public @ResponseBody
    String register(
            @RequestParam(value = "f_name") String fName, @RequestParam(value = "l_name") String lName, @RequestParam(value = "com_name", required = false, defaultValue = "") String comName, @RequestParam(value = "email") String email, @RequestParam(value = "pass") String pass, @PathVariable("type") String type
    ) throws JSONException {
        User user = new User();
        user.comname = comName;
        user.email = email;
        user.fname = fName;
        user.lname = lName;
        user.type = type;
        if (userService.register(user, pass)) {
            return "success";
        } else {
            return "fail";
        }

    }

    @RequestMapping(value = "/change/passwd", method = RequestMethod.POST)//add post when deployed
    public String changePasswd(HttpSession session, @RequestParam(value = "passwd") String passwd) throws JSONException {

        String type = (String) session.getAttribute("type");
        if (type != null) {
            int id = (Integer) session.getAttribute("id");
            userService.updatePassword(id, passwd, type);
        }
        return "redirect:/dash";
    }

    @RequestMapping(value = "/change/email", method = RequestMethod.POST)//add post when deployed
    public String changeEmail(HttpSession session, @RequestParam(value = "email") String email, ModelMap model) throws JSONException {
        String type = (String) session.getAttribute("type");
        if (type != null) {
            int id = (Integer) session.getAttribute("id");
            if (!userService.updateEmail(id, email, type)) {
                model.addAttribute("err", "Email already used");
            };

        }

        return "redirect:/dash";

    }

    @RequestMapping(value = "/getemail")//add post when deployed
    public @ResponseBody
    String getEmail(HttpSession session) throws JSONException {
        String type = (String) session.getAttribute("type");
        if (type != null) {
            int id = (Integer) session.getAttribute("id");
            return userService.getUser(id, type).email;
        }

        return "";

    }

    @RequestMapping(value = "/dash")
    public String dash(HttpSession session, ModelMap model) throws JSONException {
        String type = (String) session.getAttribute("type");
        if (type == null) {
            return "timeout";
        }
        Integer id = (Integer) session.getAttribute("id");
        User user = userService.getUser(id, type);
        model.addAttribute("email", user.email);
        if (type.equals("company")) {
            return "forward:/empd";
        } else {
            return "forward:/apyd";
        }

    }

}
