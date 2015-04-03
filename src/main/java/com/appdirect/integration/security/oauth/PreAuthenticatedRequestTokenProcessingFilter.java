package com.appdirect.integration.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.common.OAuthCodec;
import org.springframework.security.oauth.common.OAuthConsumerParameter;
import org.springframework.security.oauth.common.OAuthProviderParameter;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;
import org.springframework.security.oauth.provider.filter.UnauthenticatedRequestTokenProcessingFilter;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.security.oauth.provider.verifier.OAuthVerifierServices;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PreAuthenticatedRequestTokenProcessingFilter extends UnauthenticatedRequestTokenProcessingFilter {
    @Autowired
    private OAuthVerifierServices verifierServices;

    protected void onValidSignature(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        //signature is verified; create the token, send the response.
        ConsumerAuthentication authentication = (ConsumerAuthentication) SecurityContextHolder.getContext().getAuthentication();
        OAuthProviderToken authToken = createOAuthToken(authentication);
        if (!authToken.getConsumerKey().equals(authentication.getConsumerDetails().getConsumerKey())) {
            throw new IllegalStateException("The consumer key associated with the created auth token is not valid for the authenticated consumer.");
        }

        ///////////////////////////////////////////////////////////////////
        // Pre-authorize the Request Token for 2-Legged OAuth.
        // This will only be done if the Consumer has ExtraTrust and is not required
        // to obtain an authenticated token (see isRequiredToObtainAuthenticatedToken())
        ///////////////////////////////////////////////////////////////////

        if ((authentication.getConsumerDetails() instanceof ExtraTrustConsumerDetails) &&
                !((ExtraTrustConsumerDetails) authentication.getConsumerDetails()).isRequiredToObtainAuthenticatedToken()) {
            String verifier = verifierServices.createVerifier();
            getTokenServices().authorizeRequestToken(authToken.getValue(), verifier, authentication);
        }

        ////////////////////////////////////////////////////////////


        String tokenValue = authToken.getValue();
        String callback = authentication.getOAuthParameters().get(OAuthConsumerParameter.oauth_callback.toString());

        StringBuilder responseValue = new StringBuilder(OAuthProviderParameter.oauth_token.toString())
                .append('=')
                .append(OAuthCodec.oauthEncode(tokenValue))
                .append('&')
                .append(OAuthProviderParameter.oauth_token_secret.toString())
                .append('=')
                .append(OAuthCodec.oauthEncode(authToken.getSecret()));

        if (callback != null) {
            responseValue.append('&')
                    .append(OAuthProviderParameter.oauth_callback_confirmed.toString())
                    .append("=true");
        }

        response.setContentType(getResponseContentType());
        response.getWriter().print(responseValue.toString());
        response.flushBuffer();
    }
}
