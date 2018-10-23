package com.hust.itss.services;

import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.hust.itss.utils.CookieUtils;
import com.hust.itss.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JWTAuthenticationService {

    public static final String JWT_COOKIE_NAME = "TRANSPORT-JWT";

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${security.cookie.path}")
    private String cookiePath;

    @Value("${security.cookie.expiry-seconds}")
    private int cookieExpirySeconnds;

    @Value("${security.oauth2.cookieOverHttpsOnly}")
    private boolean cookieOverHttpsOnly;

    public String setAuthenticationData(HttpServletRequest request, HttpServletResponse response, SysUser user) throws IOException {
        CookieUtils.deleteCookie(request, response, HttpCookieOAuth2AuthorizationRequestRepository.COOKIE_NAME, cookieOverHttpsOnly);
//        CookieUtils.deleteCookie(request, responses, "JSESSIONID" ,cookieOverHttpsOnly);
        String token = jwtUtils.createTokenForUser(user);
        Cookie cookie = new Cookie(JWT_COOKIE_NAME, token);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieExpirySeconnds);

        response.addCookie(cookie);
        System.out.println("Cookie: " + cookie);
        return token;
    }
}
