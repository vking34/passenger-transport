package com.hust.itss.utils.social;

import com.hust.itss.models.users.SysUser;
import com.hust.itss.repositories.SysUserRepository;
import com.hust.itss.services.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignUpImpl implements ConnectionSignUp {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public String execute(Connection<?> connection) {
        SysUser newAccount = sysUserService.createSysUser(connection);
        return newAccount.getUsername();
    }
}
