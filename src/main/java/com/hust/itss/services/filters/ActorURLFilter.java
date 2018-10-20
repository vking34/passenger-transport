package com.hust.itss.services.filters;

import com.hust.itss.services.CustomUserDetailService;
import com.hust.itss.services.JWTAuthenticationService;
import com.hust.itss.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ActorURLFilter implements Filter {

    private CustomUserDetailService customUserDetailService;

    public ActorURLFilter(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("[CookieAuthorizationFilter]");
        String token;
        try {
            token = WebUtils.getCookie((HttpServletRequest) servletRequest, JWTAuthenticationService.JWT_COOKIE_NAME).getValue();
        }
        catch (Exception e){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        System.out.println("[CookieAuthorizationFilter] token: " + token);
        if(token == null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = JwtUtils.getAuthenticationFromToken(token, customUserDetailService);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
