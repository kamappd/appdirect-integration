package com.appdirect.integration.configuration;

import com.appdirect.integration.security.oauth.TwoLeggedConsumerDetailsService;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth.provider.OAuthProviderSupport;
import org.springframework.security.oauth.provider.filter.CoreOAuthProviderSupport;
import org.springframework.security.oauth.provider.token.InMemorySelfCleaningProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;

@PropertySource("classpath:oauth.properties")
@Configuration
public class OAuthSecurityConfiguration {

    @Value("${oauth.consumer.key}")
    private String oauthConsumerKey;
    @Value("${oauth.consumer.secret}")
    private String oauthConsumerSecret;

    @Bean
    public OAuthConsumer oAuthConsumer() {
        return new CommonsHttpOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);
    }
}
