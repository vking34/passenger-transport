package com.hust.itss.controllers.auth;

import com.hust.itss.constants.RoleContants;
import com.hust.itss.models.responses.Response;
import com.hust.itss.models.users.Client;
import com.hust.itss.models.users.UserRegisterForm;
import com.hust.itss.repositories.user.SysUserRepository;
import com.hust.itss.utils.EncryptedPasswordUtils;
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
    public Response createUser(@RequestBody UserRegisterForm  userRegisterForm){
        System.out.println("POST: create user: " + userRegisterForm.getFullName());
        Client client = new Client(
                userRegisterForm.getUsername(),
                userRegisterForm.getEmail(),
                EncryptedPasswordUtils.encryptPassword(userRegisterForm.getPassword()),
                RoleContants.CLIENT,
                userRegisterForm.getFullName(),
                userRegisterForm.getPhoneNumber(),
                userRegisterForm.getAddress(),
                1
                );
        sysUserRepository.insert(client);
        return new Response(true, 0, "/");
    }
}
