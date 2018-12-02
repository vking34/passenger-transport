package com.hust.itss.controllers.auth;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthenticationController {
    @GetMapping
    public String getLoginPage(Model model){
        return "login";
    }
}
