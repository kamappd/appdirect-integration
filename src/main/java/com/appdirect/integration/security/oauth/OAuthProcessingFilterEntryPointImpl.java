package com.appdirect.integration.security.oauth;

import org.springframework.http.MediaType;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthProcessingFilterEntryPointImpl extends OAuthProcessingFilterEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException failure)
            throws IOException, ServletException {
        // TODO Add your custom error handling here.
        // Failures here will be Spring Security's.
        // Here are some of the Exceptions you can handle:
        // InsufficientAuthenticationException

//        response.setCharacterEncoding( "UTF-8" );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(MediaType.APPLICATION_XML_VALUE);
//
//        // This response is for simplicity. Don't build your JSON responses like this! =)
//        response.getWriter().println( "{\"Unauthorized\":\"" + failure + "\"}" );
    }
}
