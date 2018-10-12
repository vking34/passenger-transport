package com.hust.itss.configs;

import com.hust.itss.constants.RoleContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/**", "/signup", "/login", "/logout").permitAll()
                .antMatchers("/userInfo").hasAnyRole(RoleContants.USER, RoleContants.ADMIN)
                .antMatchers("/admin").hasRole(RoleContants.ADMIN)
                .antMatchers(HttpMethod.GET, "/api/client", "/api/client/**").hasAnyRole(RoleContants.ADMIN, RoleContants.USER);

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Override
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }
}
