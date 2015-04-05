package com.appdirect.integration.controllers;


import com.appdirect.integration.models.Subscription;
import com.appdirect.integration.services.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionsController {

    private SubscriptionsService subscriptionsService;

    @Autowired
    public SubscriptionsController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @RequestMapping(value = "/subscriptions", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Subscription> getSubscriptions() throws Exception {
        return subscriptionsService.getSubscriptions();
    }
}
