package com.hust.itss.services.user;

import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import com.hust.itss.utils.auth.Oauth2AuthenticationUtils;
import com.hust.itss.utils.auth.SocialAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UpdateUserAsync updateUserAsync;

    @Autowired
    private SysUserRepository userRepository;

    private final Oauth2AuthenticationUtils oauthUtils;

    @Autowired
    public UserService(Oauth2AuthenticationUtils oauth2AuthenticationUtils){
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
            updateUserAsync.save(user);
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

        userRepository.insert(user);
        return user;
    }
}
