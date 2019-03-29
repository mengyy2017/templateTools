package com.templateTools.controller;

import com.templateTools.base.controller.BaseController;
import com.templateTools.base.entity.Resp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    @RequestMapping("/login")
    public String login() {
        return null;
    }

    @RequestMapping("/index")
    @ResponseBody
    public Resp<String> index() {
        return respResult.get();
    }

    @RequestMapping("/logout")
    public String logout() {
        return null;
    }

    @RequestMapping("/accDenied")
    public String accDenied(HttpServletRequest request) {
        String a = request.getParameter("firstName");
        String aa = request.getParameter("username");
        return "msg: access denied!";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String a = request.getParameter("firstName");
        String aa = request.getParameter("username");
        return "msg: 服务器出错！";
    }

}
