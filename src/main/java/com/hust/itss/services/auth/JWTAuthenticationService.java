package com.hust.itss.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.models.response.TokenResponse;
import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.hust.itss.utils.auth.CookieUtils;
import com.hust.itss.utils.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JWTAuthenticationService {

    public static final String JWT_COOKIE_NAME = "TRANSPORT-JWT";
    public static final String FULL_NAME = "full_name";
    public static final String PICTURE = "picture";

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${security.cookie.path}")
    private String cookiePath;

    @Value("${security.cookie.expiry-seconds}")
    private int cookieExpirySeconnds;

    @Value("${security.oauth2.cookieOverHttpsOnly}")
    private boolean cookieOverHttpsOnly;

    public String setAuthenticationData(HttpServletRequest request, HttpServletResponse response, SysUser user, Boolean isOAuth) throws IOException , ServletException {
        CookieUtils.deleteCookie(request, response, HttpCookieOAuth2AuthorizationRequestRepository.COOKIE_NAME, cookieOverHttpsOnly);
//        CookieUtils.deleteCookie(request, response, "JSESSIONID" ,cookieOverHttpsOnly);

        String token = jwtUtils.createTokenForUser(user);

        Cookie cookie = new Cookie(JWT_COOKIE_NAME, token);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieExpirySeconnds);
        response.addCookie(cookie);

        if (isOAuth){
            Cookie nameCookie = new Cookie(FULL_NAME, user.getFullName());
            nameCookie.setPath(cookiePath);
            nameCookie.setMaxAge(cookieExpirySeconnds);

            Cookie pictureCookie = new Cookie(PICTURE, user.getPicture());
            pictureCookie.setPath(cookiePath);
            pictureCookie.setMaxAge(cookieExpirySeconnds);

            response.addCookie(nameCookie);
            response.addCookie(pictureCookie);
        }

        return token;
    }
}
