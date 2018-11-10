package com.hust.itss.controllers.user;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class OauthUserController {

    @GetMapping
    public Principal getUser(Principal principal){
        return principal;
    }
}
