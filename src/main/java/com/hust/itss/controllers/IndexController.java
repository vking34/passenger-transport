package com.hust.itss.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String getIndex(Model model){
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "index";
    }


    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = "Username: " + loginedUser.getUsername();
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessful(Model model){
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();

        String userInfo = "Username: " + loginedUser.getUsername();
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();

            String userInfo = "Username: " + loginedUser.getUsername();
            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
            
        }

        return "403Page";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "loginPage";
    }

    @GetMapping("/signin")
    public String signInPage(Model model) {
        return "redirect:/login";
    }

}
