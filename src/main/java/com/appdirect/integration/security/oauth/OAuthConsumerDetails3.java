package com.appdirect.integration.security.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;

import java.util.List;

public class OAuthConsumerDetails3 implements ExtraTrustConsumerDetails {

    private String consumerKey;
    private SharedConsumerSecretImpl signatureSecret;
    private List<GrantedAuthority> authorities;

    public OAuthConsumerDetails3(String consumerKey,
                                 String signatureSecret, List<GrantedAuthority> authorities) {
        this.consumerKey = consumerKey;
        this.signatureSecret = new SharedConsumerSecretImpl(signatureSecret);
        this.authorities = authorities;
    }

    @Override
    public boolean isRequiredToObtainAuthenticatedToken() {
        return false;
    }

    @Override
    public String getConsumerKey() {
        return this.consumerKey;
    }

    @Override
    public String getConsumerName() {
        return "none";
    }

    @Override
    public SignatureSecret getSignatureSecret() {
        return this.signatureSecret;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
