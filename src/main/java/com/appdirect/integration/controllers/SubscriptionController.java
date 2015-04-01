package com.appdirect.integration.controllers;

import com.appdirect.integration.models.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
@RequestMapping("/api/integration/v1/events/subscriptions")
public class SubscriptionController {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @RequestMapping(value = "/", method = GET, produces = APPLICATION_XML_VALUE)
    @ResponseBody()
    public ResponseMessage handleSubscriptionOrderEvent(@RequestParam("url") String url) {
        logger.info(url);
        return new ResponseMessage(true, "toto", "1234");
    }

}
