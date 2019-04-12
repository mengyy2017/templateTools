package com.system.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.system.bussiness.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    ResourceOwnerPasswordResourceDetails resourceDetails;

    @RequestMapping("/login")
    @ResponseBody
    public Resp<String> login(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        try {
            setVals(resourceDetails, getVAndF(userEntity.getUsername(), ResourceOwnerPasswordResourceDetails::setUsername)
                    , getVAndF(userEntity.getPassword(), ResourceOwnerPasswordResourceDetails::setPassword));
            mkSuccResp(oAuth2RestTemplate.getAccessToken().getValue());
        } catch (Exception e) {
            mkFailResp(e.getMessage());
        }
        return respResult.get();
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
