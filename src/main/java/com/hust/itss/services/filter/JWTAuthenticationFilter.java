package com.hust.itss.services.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hust.itss.constants.response.ResponseHeader;
import com.hust.itss.constants.security.SecurityContants;
import com.hust.itss.models.response.Response;
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

        TokenResponse tokenResponse = new TokenResponse(true, token, sysUser);

        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(tokenResponse));
        response.addHeader(ResponseHeader.CONTENT_TYPE, ResponseHeader.APP_JSON);
        response.addHeader(SecurityContants.AUTHORIZATION, SecurityContants.TOKEN_PREFIX + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(FAIL_AUTH));
    }
}