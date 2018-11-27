package com.hust.itss.controllers.auth;

import com.hust.itss.models.response.Response;
import com.hust.itss.models.user.Client;
import com.hust.itss.models.user.RegisterForm;
import com.hust.itss.repositories.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    static final Response INVALID_USERNAME_RESPONSE = new Response(false, 1, "username exists");
    static final Response INVALID_PHONE_NUMBER_RESPONSE = new Response(false, 2, "phone number exists");
    static final Response INVALID_EMAIL_RESPONSE = new Response(false, 3, "email exists");
    static final Response SUCCESS_RESPONSE = new Response(true, null, "/");

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SignUpAsyncTasks asyncTasks;

    @PostMapping
    Response createUser(@RequestBody RegisterForm registerForm){

        System.out.println("POST: create user: " + registerForm.getFullName());

        Client user =  clientRepository.findExistingUser(registerForm.getUsername(), registerForm.getPhoneNumber(), registerForm.getEmail());
        if (user != null){
            if (user.getUsername().equals(registerForm.getUsername()))
                return INVALID_USERNAME_RESPONSE;
            if (user.getPhoneNumber().equals(registerForm.getPhoneNumber()))
                return INVALID_PHONE_NUMBER_RESPONSE;
            return INVALID_EMAIL_RESPONSE;
        }

        asyncTasks.insertUser(registerForm);
        return SUCCESS_RESPONSE;
    }
}
