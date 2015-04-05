package com.appdirect.integration.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public void handleLogin(@RequestParam String openIdUrl, @RequestParam String accountIdentifier) {
        logger.info("login in {}, {}", openIdUrl, accountIdentifier);
    }

}
