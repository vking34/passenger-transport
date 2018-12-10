package com.hust.itss.services.user;

import com.hust.itss.models.user.SysUser;
import org.springframework.stereotype.Component;

@Component
public interface UpdateUserAsync {
    public void insert(SysUser user);
    public void save(SysUser user);
}
