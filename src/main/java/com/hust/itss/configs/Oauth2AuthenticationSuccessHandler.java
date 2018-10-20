package com.hust.itss.configs;

import com.hust.itss.models.users.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("On OAuth2 Success : " + authentication);
        OAuth2AuthenticationToken auth2 = (OAuth2AuthenticationToken) authentication;
        SysUser user = userService.createOrUpdateUser(auth2);
        jwtAuthenticationService.setAuthenticationData(request, response, user);
        super.setDefaultTargetUrl(logonURL);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
