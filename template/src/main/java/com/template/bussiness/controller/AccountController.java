package com.template.bussiness.controller;

import com.common.bussiness.controller.BaseController;
import com.common.pub.pubBo.Resp;
import com.template.bussiness.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    ResourceOwnerPasswordResourceDetails resourceDetails;

    @RequestMapping("/login")
    public String login(@RequestBody UserEntity userEntity) {
        resourceDetails.setUsername(userEntity.getUsername());
        resourceDetails.setPassword(userEntity.getPassword());
//        resourceDetails.setTokenName("token");
        OAuth2AccessToken oAuth2AccessToken = oAuth2RestTemplate.getAccessToken();
        String accessToken = oAuth2AccessToken.getValue();
//        oAuth2RestTemplate.getResource().
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
