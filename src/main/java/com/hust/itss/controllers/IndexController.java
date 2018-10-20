package com.hust.itss.controllers;


import com.hust.itss.constants.SecurityContants;
import com.hust.itss.services.JWTAuthenticationService;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.hust.itss.constants.RoleContants.*;

@Controller
@RequestMapping
public class IndexController {
    @GetMapping
    public String getIndex(@CookieValue(value = JWTAuthenticationService.JWT_COOKIE_NAME, required = false) String token, Model model){
        System.out.println("GET: index with token: " + token);
        if(token != null){
            String role = Jwts.parser().setSigningKey(SecurityContants.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getAudience();
            System.out.println("role : " + role);
            switch (role) {
                case ADMIN:
                    return "redirect:/admin";

                case DRIVER:
                    return "redirect:/driver";

                case ASSISTANT:
                    return "redirect:/assistant";

                case ROOT:
                    return "redirect:/root";

                case USER:
                case CLIENT:
                    return "redirect:/client";

                default:
                    return "redirect:/login";
            }
        }

        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "index";
    }

    @GetMapping("/admin")
    public String getAdminPage(HttpServletResponse response,
                               @CookieValue(value = JWTAuthenticationService.JWT_COOKIE_NAME, required = false) String token,
                               Model model){
        System.out.println("GET: admin with token: " + token);
        if (token == null){
            response.setStatus(403);
        }



        return "admin";
    }

    @GetMapping("/driver")
    public String getDriverPage(@CookieValue(value = JWTAuthenticationService.JWT_COOKIE_NAME) String token, Model model){
        System.out.println("GET: Driver with token: " + token);
        if (token == null){
            return "redirect:/login";
        }

        String role = Jwts.parser().setSigningKey(SecurityContants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getAudience();
        System.out.println("role : " + role);


        return "driver";
    }

    @GetMapping("/assistant")
    public String getAssistantPage(@CookieValue(value = JWTAuthenticationService.JWT_COOKIE_NAME) String token, Model model){
        System.out.println("GET: assistant with token: " + token);
        if (token == null){
            return "redirect:/login";
        }

        return "assistant";
    }

    @GetMapping("/client")
    public String getClientPage(@CookieValue(value = JWTAuthenticationService.JWT_COOKIE_NAME) String token, Model model){
        System.out.println("GET: client with token: " + token);
        if (token == null){
            return "redirect:/login";
        }

        return "client";
    }
}
