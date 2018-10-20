package com.hust.itss.services;

import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.hust.itss.constants.OAuthProvider.FACEBOOK;
import static com.hust.itss.constants.OAuthProvider.GOOGLE;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    public UserDetails loadUserByUsernameAndProvider(String username, String provider) throws UsernameNotFoundException {
        SysUser user = Optional.ofNullable(sysUserRepository.findSysUserByUsernameAndAuthProvider(username, provider)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.isActive()){
            throw new UsernameNotFoundException("User: " + username + " not active");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + user.getRole());

        return new User(username, user.getPassword(), authorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = Optional.ofNullable(sysUserRepository.findSysUserByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.isActive()){
            throw new UsernameNotFoundException("User: " + username + " not active");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + user.getRole());

        return new User(username, user.getPassword(), authorities);
    }
}
