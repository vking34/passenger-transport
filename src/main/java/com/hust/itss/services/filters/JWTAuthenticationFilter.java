package com.hust.itss.services.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.constants.SecurityContants;
import com.hust.itss.models.responses.Response;
import com.hust.itss.models.responses.TokenResponse;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import com.hust.itss.services.JWTAuthenticationService;
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
        System.out.println("[attemptAuthentication]");
        try {
            SysUser user = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);

            System.out.println("attempt Authentication: {username: " + user.getUsername() + ", password: " + user.getPassword() + "}");
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            Response resp = new Response(false, 1, "Require JSON format");
            try {
                response.getWriter().write(resp.toString());
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
        System.out.println("successfulAuthentication : " + username);

        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);
        String token = jwtAuthenticationService.setAuthenticationData(request, response, sysUser);

        TokenResponse tokenResponse = new TokenResponse(true, token);
        response.getWriter().write(tokenResponse.toString());
        response.addHeader(SecurityContants.AUTHORIZATION, SecurityContants.TOKEN_PREFIX + token);
//        responses.sendRedirect("https://localhost/");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Response resp = new Response(false, 1, "username or password is incorrect...");
        response.getWriter().write(resp.toString());
    }
}