package com.hust.itss.services;

import com.hust.itss.constants.RoleContants;
import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import com.hust.itss.utils.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;

    public SysUser createSysUser(Connection<?> connection){
        ConnectionKey key = connection.getKey();

        System.out.println("ConnectionKey= " + key.getProviderId() + ", " + key.getProviderUserId());

        UserProfile userProfile = connection.fetchUserProfile();

        String email = userProfile.getEmail();

        SysUser sysUser = sysUserRepository.findSysUserByEmail(email);
        if(sysUser != null){
            return sysUser;
        }

        String encryptedPassword = EncryptedPasswordUtils.encryptPassword("123");
        sysUser = new SysUser(email,email, encryptedPassword, RoleContants.USER, true);
        sysUserRepository.insert(sysUser);

        return sysUser;
    }
}
