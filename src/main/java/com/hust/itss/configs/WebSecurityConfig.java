package com.hust.itss.configs;

import com.hust.itss.constants.RoleContants;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.hust.itss.services.CustomUserDetailService;
import com.hust.itss.services.JWTAuthenticationService;
import com.hust.itss.services.JWTAuthorizationFilter;
import com.hust.itss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Value("${security.client.enable-cors}")
    private boolean enableCORS;

    @Value("${security.client.logon-url}")
    private String logonURL;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(enableCORS)
        {
            http.cors().and().csrf().disable();
        }

        http
                .oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint().baseUri("/login")
                .authorizationRequestRepository(cookieBasedAuthorizationRequestRepository())
                .and()
        .successHandler(new Oauth2AuthenticationSuccessHandler())
        .and()
                .logout()
                .permitAll()
                .and()
        .httpBasic().disable()

        .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/transporter").hasAnyRole(RoleContants.USER, RoleContants.ADMIN)
                .antMatchers(HttpMethod.GET,"/api/employee").hasAnyRole(RoleContants.ADMIN)
        .and()
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService))
        ;
    }

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

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieBasedAuthorizationRequestRepository() {
        // Using cookie based repository to avoid data being put into HTTP session
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}

