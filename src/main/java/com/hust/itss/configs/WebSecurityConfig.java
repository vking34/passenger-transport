package com.hust.itss.configs;

import com.hust.itss.constants.security.RoleContants;
import com.hust.itss.constants.request.EntryPoints;
import com.hust.itss.controllers.auth.CustomLogoutHandler;
import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import com.hust.itss.repositories.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.hust.itss.services.auth.JWTAuthenticationService;
import com.hust.itss.services.filter.CookieAuthorizationFilter;
import com.hust.itss.services.filter.JWTAuthenticationFilter;
import com.hust.itss.services.filter.JWTAuthorizationFilter;
import com.hust.itss.services.user.CustomUserDetailService;
import com.hust.itss.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

    @Autowired
    private SysUserRepository sysUserRepository;

    @Value("${security.client.enable-cors}")
    private boolean enableCORS;

    @Value("${security.client.logon-url}")
    private String logonURL;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
        http.csrf().disable();

        // Authentication
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), sysUserRepository, jwtAuthenticationService));

        // OAuth2
        http
                .oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint().baseUri("/login")
                .authorizationRequestRepository(cookieBasedAuthorizationRequestRepository())
                .and()
        .successHandler(new Oauth2AuthenticationSuccessHandler())
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout" ))
                .addLogoutHandler(new CustomLogoutHandler())
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
        .httpBasic().disable();

        // filter cookies for actors
        http.authorizeRequests()

                .antMatchers(HttpMethod.GET, "/admin").hasRole(RoleContants.ADMIN)
                .antMatchers(HttpMethod.GET, "/driver").hasAnyRole(RoleContants.DRIVER, RoleContants.ADMIN)
                .antMatchers(HttpMethod.GET, "/assistant").hasAnyRole(RoleContants.ASSISTANT, RoleContants.ADMIN)
                .antMatchers(HttpMethod.GET, "/client").hasAnyRole(RoleContants.CLIENT, RoleContants.USER, RoleContants.ADMIN)
                .and()
                .addFilter(new CookieAuthorizationFilter(authenticationManager(), customUserDetailService));

        // for access token for API
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/me").hasAnyRole(RoleContants.USER, RoleContants.CLIENT, RoleContants.DRIVER, RoleContants.ASSISTANT, RoleContants.ADMIN)
//                .antMatchers(HttpMethod.GET,"/api/transporter").hasAnyRole(RoleContants.USER, RoleContants.ADMIN, RoleContants.DRIVER)
//                .antMatchers(HttpMethod.GET,"/api/employee").hasAnyRole(RoleContants.ADMIN)
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService))
        ;
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(HttpMethod.GET, EntryPoints.UNSECURE_ENTRYPOINTS);
    }

    // encode password before attempt authentication (searching for db)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            OAuth2AuthenticationToken auth2 = (OAuth2AuthenticationToken) authentication;
            SysUser user = userService.createOrUpdateUser(auth2);
            jwtAuthenticationService.setAuthenticationData(request, response, user, true);
            super.setDefaultTargetUrl(logonURL);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieBasedAuthorizationRequestRepository() {
        // Using cookie based repository to avoid data being put into HTTP session
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

