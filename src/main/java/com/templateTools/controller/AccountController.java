package com.templateTools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @RequestMapping("/login")
    public String login() {
        return null;
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "success";
    }

    @RequestMapping("/logout")
    public String logout() {
        return null;
    }

    @RequestMapping("/accDenied")
    public String accDenied(HttpServletRequest request) {
        String a = request.getParameter("firstName");
        String aa = request.getParameter("username");
        return "message: access denied!";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String a = request.getParameter("firstName");
        String aa = request.getParameter("username");
        return "message: 服务器出错！";
    }

}
