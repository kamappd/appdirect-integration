package com.appdirect.integration.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login/*")
    public void handleRealmRequest() {

    }

    @RequestMapping("/login/{openid}")
    public void initiateLogin(@PathVariable("openid") String openId, @RequestParam("requestIdentifier") String accountIdentifier) {

    }
}
