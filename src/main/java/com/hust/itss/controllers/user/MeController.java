package com.hust.itss.controllers.user;

import com.hust.itss.models.user.SysUser;
import com.hust.itss.repositories.user.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MeController {
    @Autowired
    private SysUserRepository userRepository;

    @GetMapping("/me")
    SysUser getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findSysUserByUsername(username);
    }
}
