package com.hust.itss.services;

import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import com.hust.itss.utils.Oauth2AuthenticationUtils;
import com.hust.itss.utils.SocialAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final SysUserRepository sysUserRepository;
    private final Oauth2AuthenticationUtils oauthUtils;

    @Autowired
    public UserService(SysUserRepository sysUserRepository, Oauth2AuthenticationUtils oauth2AuthenticationUtils){
        this.sysUserRepository = sysUserRepository;
        this.oauthUtils = oauth2AuthenticationUtils;
    }

    public SysUser createOrUpdateUser(OAuth2AuthenticationToken authentication) {

        Map<String, Object> userDetails = oauthUtils.getUserDetails(authentication);
        System.out.println(userDetails);
        SysUser.AuthenticationProvider provider = oauthUtils.getAuthenticationProvider(authentication);
        Optional<SysUser> user = oauthUtils.getUserIfExists(authentication);
        return user.map(user1 -> updateUser(userDetails, user1, provider)).orElseGet(() -> createUser(userDetails, provider));
    }

    private SysUser updateUser(Map<String, Object> userDetails, SysUser user, SysUser.AuthenticationProvider provider) {
        System.out.println("update User");
        boolean update = false;
        switch (provider) {
            case GOOGLE: {
                update = SocialAuthUtils.updateGoogleUser(userDetails, user);
                break;
            }
            case FACEBOOK: {
                update = SocialAuthUtils.updateFacebookUser(userDetails, user);
                break;
            }
            default: {
                System.out.println("Unexpected oauth user type {}" + provider);
                throw new IllegalArgumentException("Unexpected oauth type: " + provider);
            }
        }
        if (update)
            sysUserRepository.save(user);
        return user;
    }

    private SysUser createUser(Map<String, Object> userDetails, SysUser.AuthenticationProvider provider) {

        SysUser user;

        switch (provider) {
            case GOOGLE: {
                user = SocialAuthUtils.createGoogleUser(userDetails);
                break;
            }
            case FACEBOOK: {
                user = SocialAuthUtils.createFacebookUser(userDetails);
                break;
            }
            default: {
                System.out.println("Unexpected oauth user type {}" + provider);
                throw new IllegalArgumentException("Unexpected oauth type: " + provider);
            }
        }

        sysUserRepository.insert(user);
        return user;
    }
}
