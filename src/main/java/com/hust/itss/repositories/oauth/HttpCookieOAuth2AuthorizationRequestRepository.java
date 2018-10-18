package com.hust.itss.repositories.oauth;

import com.hust.itss.models.users.SysUser;
import com.hust.itss.services.JWTAuthenticationService;
import com.hust.itss.services.UserService;
import com.hust.itss.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

/**
 * Based on https://stackoverflow.com/questions/49095383/spring-security-5-stateless-oauth2-login-how-to-implement-cookies-based-author
 */
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Value("${security.oauth2.cookieOverHttpsOnly}")
    private boolean cookieOverHttpsOnly;

    public static final String COOKIE_NAME = "passenger-transport-service-oauth2";

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.fetchCookie(request, COOKIE_NAME)
                .map(this::toOAuth2AuthorizationRequest)
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                         HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, COOKIE_NAME, cookieOverHttpsOnly);
            return;
        }
        System.out.println("Save authorization request and create cookie : " + authorizationRequest);

        Cookie cookie = new Cookie(COOKIE_NAME, fromAuthorizationRequest(authorizationRequest));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieOverHttpsOnly);
        cookie.setMaxAge(-1);   // Expire when browser closed - bug in API means explicit removal not possible
        response.addCookie(cookie);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {

        // Question: How to remove the cookie, because we don't have access to responses object here.
        // This seems to be a flaw in the design of the AuthorizationRequestRepository interface
        // as the default behaviour is to remove data from the HTTP session -
        // which will be accessed via the request object. Here we
        // want to clear out a cookie for which we need access to the responses object.
        // So, for the time being, another unrelated part of the code base clears the cookie -
        // see the JwtAuthenticationService class for details.
        // There is an issue raised on Spring Security for this and the interface may be
        // uplifted in 5.1 - see https://github.com/spring-projects/spring-security/issues/5313
        return loadAuthorizationRequest(request);
    }

    private String fromAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest) {

        return Base64.getUrlEncoder().encodeToString(
                SerializationUtils.serialize(authorizationRequest));
    }



    private OAuth2AuthorizationRequest toOAuth2AuthorizationRequest(Cookie cookie) {

        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue()));
    }
}