package com.appdirect.integration;

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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;

@Service
public class EventDataRetrieverService {

    @Autowired
    private OAuthConsumer oAuthConsumer;

    public <T> T getEventData(String url, Class<T> eventClass) throws JAXBException, IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpRequest = (HttpGet) oAuthConsumer.sign(new HttpGet(url)).unwrap();

            try (CloseableHttpResponse response = httpclient.execute(httpRequest)) {
                if (response.getStatusLine().getStatusCode() == 200) {

                    JAXBContext context = JAXBContext.newInstance(eventClass);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    return (T) unmarshaller.unmarshal(response.getEntity().getContent());
                }

            }
        }
        return null;
    }

}
