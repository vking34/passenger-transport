package com.hust.itss.controllers.auth;

import com.hust.itss.services.JWTAuthenticationService;
import com.hust.itss.utils.CookieUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        System.out.println("LOGOUT");
        CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, JWTAuthenticationService.JWT_COOKIE_NAME, true);
        httpServletResponse.setHeader("", "");
    }
}
