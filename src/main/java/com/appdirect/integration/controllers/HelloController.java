package com.appdirect.integration.controllers;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class HelloController {

    @Autowired
    private OAuthConsumer oAuthConsumer;

    @RequestMapping("/")
    @ResponseBody
    String home() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/dummyChange");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("Accept", "application/xml");
        oAuthConsumer.sign(request);
        request.connect();

        return request.getResponseMessage();
    }


}
