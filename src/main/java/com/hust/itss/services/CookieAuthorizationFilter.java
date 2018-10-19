package com.hust.itss.services;

import com.hust.itss.constants.SecureEndpoints;
import com.hust.itss.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.web.util.WebUtils;

public class CookieAuthorizationFilter extends BasicAuthenticationFilter {

    private CustomUserDetailService customUserDetailService;

    public CookieAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        System.out.println("[CookieAuthorizationFilter]");
        String token;
        try {
             token = WebUtils.getCookie(request, JWTAuthenticationService.JWT_COOKIE_NAME).getValue();
        }
        catch (Exception e){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("[CookieAuthorizationFilter] token: " + token);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = JwtUtils.getAuthenticationFromToken(token, customUserDetailService);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}