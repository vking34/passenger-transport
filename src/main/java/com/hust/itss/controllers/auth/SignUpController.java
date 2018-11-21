package com.hust.itss.controllers.auth;

import com.hust.itss.constants.security.RoleContants;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.user.Client;
import com.hust.itss.models.user.RegisterForm;
import com.hust.itss.repositories.user.SysUserRepository;
import com.hust.itss.utils.auth.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    private SysUserRepository sysUserRepository;

    @PostMapping
    public Response createUser(@RequestBody RegisterForm registerForm){
        System.out.println("POST: create user: " + registerForm.getFullName());
        Client client = new Client(
                registerForm.getUsername(),
                registerForm.getEmail(),
                EncryptedPasswordUtils.encryptPassword(registerForm.getPassword()),
                RoleContants.CLIENT,
                registerForm.getFullName(),
                registerForm.getPhoneNumber(),
                registerForm.getAddress(),
                1
                );
        sysUserRepository.insert(client);
        return new Response(true, 0, "/");
    }
}
