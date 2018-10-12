package com.hust.itss.services;

import com.hust.itss.models.social.SocialUserDetailsImpl;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsService: loadUserByUsername" + username);
        SysUser sysUser = sysUserRepository.findSysUserByUsername(username);

        if(sysUser == null){
            System.out.println("User: " + username+ " not found!" );
            throw new UsernameNotFoundException(username + " was not found");
        }

        System.out.println("GOT sysUser : " + sysUser.getUsername());
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_"+ sysUser.getRole());

        return new SocialUserDetailsImpl(sysUser, authorities);
    }
}
