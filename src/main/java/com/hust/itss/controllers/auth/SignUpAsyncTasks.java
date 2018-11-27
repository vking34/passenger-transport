package com.hust.itss.controllers.auth;

import com.hust.itss.models.user.RegisterForm;
import org.springframework.stereotype.Component;

@Component
public interface SignUpAsyncTasks {
    void insertUser(RegisterForm registerForm);

}
