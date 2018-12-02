package com.hust.itss.models.user;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class UserTest {
    private User user = new User("user1", "vking3415@gmail.com", "pass", "ROLE_USER", "Le Van Vuong", "1234567890", "12 Hoang Mai");

    @Test
    public void createUser(){
        assertNotNull(user);
    }

    @Test
    public void createUser1(){
        User user1 = new User(null, null, null, null, null, null, null);
        assertNotNull(user1);
    }
}
