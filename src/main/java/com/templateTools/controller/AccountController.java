package com.templateTools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @RequestMapping("/login")
    public String login() {
        return null;
    }

    @RequestMapping("/index")
    public String index() {
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
