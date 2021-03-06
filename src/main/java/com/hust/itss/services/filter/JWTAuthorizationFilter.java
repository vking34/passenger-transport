package com.hust.itss.services.filter;

import com.hust.itss.constants.security.SecurityContants;
import com.hust.itss.services.user.CustomUserDetailService;
import com.hust.itss.utils.auth.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
//        System.out.println("[JWTAuthorizationFilter]");
        String authorization = request.getHeader(SecurityContants.AUTHORIZATION);
        if(authorization == null || !authorization.startsWith(SecurityContants.TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(SecurityContants.AUTHORIZATION);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = JwtUtils.getAuthenticationFromToken(token, customUserDetailService);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

}
