package com.hust.itss.controllers.user;

import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SysUserController {

    @Autowired
    private SysUserRepository sysUserRepository;

    @GetMapping
    protected Page<SysUser> getAllUser(){
        System.out.println("GET: All user");
        PageRequest pageRequest = new PageRequest(0, 10);
        return sysUserRepository.findAll(pageRequest);
    }
}
