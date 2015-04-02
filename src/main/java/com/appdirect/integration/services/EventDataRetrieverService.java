package com.appdirect.integration.services;

import com.appdirect.integration.utils.EventParser;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class EventDataRetrieverService {

    private OAuthConsumer oAuthConsumer;
    private EventParser eventParser;

    @Autowired
    public EventDataRetrieverService(OAuthConsumer oAuthConsumer, EventParser eventParser) {
        this.oAuthConsumer = oAuthConsumer;
        this.eventParser = eventParser;
    }

    public <T> T getEventData(String url, Class<T> eventClass) throws JAXBException, IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpRequest = (HttpGet) oAuthConsumer.sign(new HttpGet(url)).unwrap();

            try (CloseableHttpResponse response = httpclient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == 200) {

                    return eventParser.parse(response.getEntity().getContent(), eventClass);
                }

            }
        }
        return null;
    }

}
