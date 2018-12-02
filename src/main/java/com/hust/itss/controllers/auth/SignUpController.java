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

import static com.hust.itss.constants.response.RegistResponse.INVALID_EMAIL_RESPONSE;
import static com.hust.itss.constants.response.RegistResponse.INVALID_PHONE_NUMBER_RESPONSE;
import static com.hust.itss.constants.response.RegistResponse.INVALID_USERNAME_RESPONSE;
import static com.hust.itss.constants.response.RegistResponse.SUCCESS_RESPONSE;

@RestController
@RequestMapping("/signup")
public class SignUpController {

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
