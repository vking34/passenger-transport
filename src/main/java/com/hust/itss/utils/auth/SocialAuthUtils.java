package com.hust.itss.utils.auth;

import com.hust.itss.constants.security.RoleContants;
import com.hust.itss.models.user.SysUser;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.hust.itss.models.user.SysUser.AuthenticationProvider.FACEBOOK;
import static com.hust.itss.models.user.SysUser.AuthenticationProvider.GOOGLE;

public class SocialAuthUtils {

    private static final String EMAIL = "email";
    private static final String PICTURE = "picture";
    private static final String LINK = "link";
    private static final String NAME = "name";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String GIVEN_NAME = "given_name";
    private static final String FAMILY_NAME = "family_name";
    private static final String GOOGLE_AUTH_ID = "sub";
    private static final String FACEBOOK_AUTH_ID = "id";
    private static final String DEFAULT_PWD = "test";

    public static SysUser createFacebookUser(Map<String, Object> userDetails) {
        String pictureUrl = extractFaceBookPictureUrl(userDetails);
        return new SysUser(
                (String) userDetails.get(EMAIL),
                (String) userDetails.get(EMAIL),
                EncryptedPasswordUtils.encryptPassword(DEFAULT_PWD),
                RoleContants.USER,
                true,
                (String) userDetails.get(FIRST_NAME),
                (String) userDetails.get(LAST_NAME),
                (String) userDetails.get(NAME),
                pictureUrl,
                (String) userDetails.get(FACEBOOK_AUTH_ID),
                FACEBOOK,
                LocalDateTime.now()
        );
    }

    public static SysUser createGoogleUser(Map<String, Object> userDetails) {
        return new SysUser(
                (String) userDetails.get(EMAIL),
                (String) userDetails.get(EMAIL),
                EncryptedPasswordUtils.encryptPassword(DEFAULT_PWD),
                RoleContants.USER,
                true,
                (String) userDetails.get(GIVEN_NAME),
                (String) userDetails.get(FAMILY_NAME),
                (String) userDetails.get(NAME),
                (String) userDetails.get(PICTURE),
                (String) userDetails.get(GOOGLE_AUTH_ID),
                GOOGLE,
                LocalDateTime.now()
        );
    }

    public static boolean updateFacebookUser(Map<String, Object> userDetails, SysUser user) {
        boolean update = false;
        if (user.getEmail().equals((String) userDetails.get(EMAIL))){
            user.setEmail((String) userDetails.get(EMAIL));
            user.setUsername((String) userDetails.get(EMAIL));
            update = true;
        }

        String pictureUrl = extractFaceBookPictureUrl(userDetails);
        if(user.getPicture().equals(pictureUrl) && pictureUrl != null){
            user.setPicture(pictureUrl);
            update = true;
        }

        return update;
    }

    public static boolean updateGoogleUser(Map<String, Object> userDetails, SysUser user) {
        boolean update = false;
        if (user.getEmail().equals((String) userDetails.get(EMAIL))){
            user.setEmail((String) userDetails.get(EMAIL));
            user.setUsername((String) userDetails.get(EMAIL));
            update = true;
        }

        if (user.getPicture().equals((String) userDetails.get(PICTURE))){
            user.setPicture((String) userDetails.get(PICTURE));
            update = true;
        }

        return update;
    }

    private static String extractFaceBookPictureUrl(Map<String, Object> userDetails) {
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
