package com.templateTools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @RequestMapping("/login")
    public String login() {
        return null;
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        request.getParameter("username");

        return null;
    }

    @RequestMapping("/logout")
    public String logout() {
        return null;
    }

    @RequestMapping("/accDenied")
    public String accDenied() {
        return null;
    }

}
