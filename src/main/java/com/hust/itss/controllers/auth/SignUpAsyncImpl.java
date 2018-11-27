package com.hust.itss.controllers.auth;

import com.hust.itss.constants.security.RoleContants;
import com.hust.itss.models.user.Client;
import com.hust.itss.models.user.RegisterForm;
import com.hust.itss.repositories.user.ClientRepository;
import com.hust.itss.utils.auth.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SignUpAsyncImpl implements SignUpAsyncTasks {

    @Autowired
    private ClientRepository clientRepository;

    @Async
    @Override
    public void insertUser(RegisterForm registerForm) {
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
        clientRepository.insert(client);
    }
}
