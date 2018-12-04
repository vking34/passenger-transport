package com.hust.itss.services.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.constants.response.ErrorResponse;
import com.hust.itss.constants.response.ResponseHeader;
import com.hust.itss.constants.security.SecurityContants;
import com.hust.itss.models.response.TokenResponse;
import com.hust.itss.models.user.AuthRequest;
import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import com.hust.itss.services.auth.JWTAuthenticationService;
import static com.hust.itss.constants.response.CommonResponse.FAIL_AUTH;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final TokenResponse SUCCESS_RESPONSE = new TokenResponse(true);

    private AuthenticationManager authenticationManager;
    private SysUserRepository sysUserRepository;
    private JWTAuthenticationService jwtAuthenticationService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SysUserRepository sysUserRepository, JWTAuthenticationService jwtAuthenticationService) {
        this.authenticationManager = authenticationManager;
        this.sysUserRepository = sysUserRepository;
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    // {"username": "test", "password": "test"}
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest user = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.addHeader(ResponseHeader.CONTENT_TYPE, ResponseHeader.APP_JSON);
                response.getWriter().write(OBJECT_MAPPER.writeValueAsString(ErrorResponse.REQUIRED_JSON));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();

        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);
        String token = jwtAuthenticationService.setAuthenticationData(request, response, sysUser);

        SUCCESS_RESPONSE.setToken(token);
        SUCCESS_RESPONSE.setSysUser(sysUser);
        response.addHeader(ResponseHeader.CONTENT_TYPE, ResponseHeader.APP_JSON);
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(SUCCESS_RESPONSE));
        response.addHeader(SecurityContants.AUTHORIZATION, SecurityContants.TOKEN_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.addHeader(ResponseHeader.CONTENT_TYPE, ResponseHeader.APP_JSON);
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(FAIL_AUTH));
    }
}