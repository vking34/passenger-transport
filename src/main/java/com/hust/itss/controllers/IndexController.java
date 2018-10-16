package com.hust.itss.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class IndexController {

    @GetMapping
    public String getIndex(Model model){
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "index";
    }
}
