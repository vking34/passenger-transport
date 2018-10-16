package com.hust.itss.services;

import com.hust.itss.constants.RoleContants;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import com.hust.itss.utils.EncryptedPasswordUtils;
import com.hust.itss.utils.oauth.Oauth2AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.hust.itss.models.users.SysUser.AuthenticationProvider.GOOGLE;
import static com.hust.itss.models.users.SysUser.AuthenticationProvider.FACEBOOK;

@Service
public class UserService {

    private static final String EMAIL = "email";
    private static final String PICTURE = "picture";
    private static final String LINK = "link";
    private static final String NAME = "name";

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
        switch (provider) {
            case GOOGLE: {
                updateGoogleUser(userDetails, user);
                break;
            }
            case FACEBOOK: {
                updateFacebookUser(userDetails, user);
                break;
            }
            default: {
//                LOGGER.error("Unexpected oauth user type {}", provider);
                System.out.println("Unexpected oauth user type {}" + provider);
                throw new IllegalArgumentException("Unexpected oauth type: " + provider);
            }
        }

        sysUserRepository.save(user);
        return user;
    }



    private SysUser createUser(Map<String, Object> userDetails, SysUser.AuthenticationProvider provider) {

        SysUser user;

        switch (provider) {
            case GOOGLE: {
                user = createGoogleUser(userDetails);
                break;
            }
            case FACEBOOK: {
                user = createFacebookUser(userDetails);
                break;
            }
            default: {
//                LOGGER.error("Unexpected oauth user type {}", provider);
                System.out.println("Unexpected oauth user type {}" + provider);
                throw new IllegalArgumentException("Unexpected oauth type: " + provider);
            }
        }

        sysUserRepository.insert(user);
        return user;
    }

    private SysUser createFacebookUser(Map<String, Object> userDetails) {

        System.out.println("Create fb user...");
        String pictureUrl = extractFaceBookPictureUrl(userDetails);

        return new SysUser(
                (String) userDetails.get(EMAIL),
                (String) userDetails.get(EMAIL),
                EncryptedPasswordUtils.encryptPassword("test"),
                RoleContants.USER,
                true,
                (String) userDetails.get("first_name"),
                (String) userDetails.get("last_name"),
                (String) userDetails.get(NAME),
                (String) userDetails.get(LINK),
                pictureUrl,
                (String) userDetails.get("id"),
                FACEBOOK,
                LocalDateTime.now()
        );
    }

    private SysUser createGoogleUser(Map<String, Object> userDetails) {
        System.out.println("Create google user...");
        return new SysUser(
                (String) userDetails.get(EMAIL),
                (String) userDetails.get(EMAIL),
                EncryptedPasswordUtils.encryptPassword("test"),
                RoleContants.USER,
                true,
                (String) userDetails.get("given_name"),
                (String) userDetails.get("family_name"),
                (String) userDetails.get(NAME),
                (String) userDetails.get(LINK),
                (String) userDetails.get(PICTURE),
                (String) userDetails.get("sub"),
                GOOGLE,
                LocalDateTime.now()
        );
    }

    private void updateFacebookUser(Map<String, Object> userDetails, SysUser user) {
        user.setEmail((String) userDetails.get(EMAIL));
        user.setUsername((String) userDetails.get(EMAIL));
        user.setFirstName((String) userDetails.get("first_name"));
        user.setLastName((String) userDetails.get("last_name"));
        user.setFullName((String) userDetails.get(NAME));
        user.setLink((String) userDetails.get(LINK));
        String url = extractFaceBookPictureUrl(userDetails);
        if (url != null) {
            user.setPicture(url);
        }
    }

    private void updateGoogleUser(Map<String, Object> userDetails, SysUser user) {
        user.setEmail((String) userDetails.get(EMAIL));
        user.setUsername((String) userDetails.get(EMAIL));
        user.setFirstName((String) userDetails.get("given_name"));
        user.setLastName((String) userDetails.get("family_name"));
        user.setFullName((String) userDetails.get(NAME));
        user.setLink((String) userDetails.get("link"));
        user.setPicture((String) userDetails.get(PICTURE));
    }

    private String extractFaceBookPictureUrl(Map<String, Object> userDetails) {
        if (userDetails.get(PICTURE) instanceof LinkedHashMap) {
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, Object> picture = (LinkedHashMap<String, Object>) userDetails.get(PICTURE);
            if (picture.get("data") instanceof LinkedHashMap) {
                @SuppressWarnings("unchecked")
                LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) picture.get("data");
                return (String) data.get("url");
            }
        }
        return null;
    }

}
