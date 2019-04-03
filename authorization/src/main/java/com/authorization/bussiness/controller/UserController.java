package com.authorization.bussiness.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping("/me")
    public Principal user2(OAuth2Authentication principal) {
        return principal;
    }
}
