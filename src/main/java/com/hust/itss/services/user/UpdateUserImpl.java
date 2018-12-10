package com.hust.itss.services.user;

import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserImpl implements UpdateUserAsync {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Async
    @Override
    public void insert(SysUser user) {
        sysUserRepository.insert(user);
    }

    @Override
    public void save(SysUser user) {
        sysUserRepository.save(user);
    }
}
