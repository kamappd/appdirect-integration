package com.appdirect.integration.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import static java.util.Arrays.asList;

public class TwoLeggedConsumerDetailsService implements ConsumerDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(TwoLeggedConsumerDetailsService.class);
    private String consumerKey;
    private String consumerSecret;

    @Value("${oauth.consumer.key}")
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @Value("${oauth.consumer.secret}")
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
        if (consumerKey == null)
            throw new OAuthException("No credentials found for the consumer key [" + consumerKey + "]");

        if (!consumerKey.equals(this.consumerKey))
            throw new OAuthException("No credentials found for the consumer key [" + consumerKey + "]");


        logger.info("Creating ConsumerDetails");

        return new OAuthConsumerDetails(
                consumerKey,
                consumerSecret,
                asList(new SimpleGrantedAuthority("ROLE_OAUTH")));
    }
}
