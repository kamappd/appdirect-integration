package com.appdirect.integration.configuration;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:oauth.properties")
@Configuration
public class OAuthConsumerConfiguration {

    @Value("${oauth.consumer.key}")
    private String oauthConsumerKey;
    @Value("${oauth.consumer.secret}")
    private String oauthConsumerSecret;

    @Bean
    public OAuthConsumer oAuthConsumer() {
        return new DefaultOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);
    }
}
