package com.templateTools.controller;

import com.templateTools.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
        String a = request.getParameter("username");

        return null;
    }

    @RequestMapping("/logout")
    public String logout() {
        return null;
    }

    @RequestMapping("/accDenied")
    public String accDenied(HttpServletRequest request) {
        String a = request.getParameter("firstName");
        String aa = request.getParameter("username");
        return null;
    }

}
