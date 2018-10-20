//package com.hust.itss.configs;
//
//import com.hust.itss.services.CookieAuthorizationFilter;
//import com.hust.itss.services.CustomUserDetailService;
//import com.hust.itss.services.filters.ActorURLFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@Configuration
//public class UrlFilterConfig {
//
//    @Autowired
//    private CustomUserDetailService customUserDetailService;
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new CookieAuthorizationFilter(customUserDetailService));
//
//        registrationBean.addUrlPatterns("/admin");
//        registrationBean.addUrlPatterns("/driver");
//
//        registrationBean.setOrder();
//        return registrationBean;
//    }
//}
