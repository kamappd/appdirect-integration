package com.appdirect.integration.security.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

import java.util.ArrayList;
import java.util.List;

public class TwoLeggedConsumerDetailsService implements ConsumerDetailsService {

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

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_OAUTH"));

        return new OAuthConsumerDetails(
                consumerKey,
                consumerSecret,
                authorities);
    }
}
