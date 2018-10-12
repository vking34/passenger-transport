package com.hust.itss.repositories;

import com.hust.itss.models.users.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends MongoRepository<SysUser, String> {
    public SysUser findSysUserByUsername(String username);
    public SysUser findSysUserByEmail(String email);
}
