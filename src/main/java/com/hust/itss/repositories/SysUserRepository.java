package com.hust.itss.repositories;

import com.hust.itss.models.users.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends MongoRepository<SysUser, String> {
    public SysUser findSysUserByUsername(String username);
    public SysUser findSysUserByEmail(String email);

    List<SysUser> findAllByAuthenticationServiceIdAndAuthProvider(String authenticationServiceId, String authenticationProvider);
//    List<User> findAllByAdminEmailedAboutSignupIsFalse();
}
