package com.hust.itss.services;

import com.hust.itss.models.social.SocialUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException, DataAccessException {
        System.out.println("SocialUserDetailsServiceImpl.loadUserByUserId=" + username);

        UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserByUsername(username);
        return (SocialUserDetailsImpl) userDetails;
    }
}
