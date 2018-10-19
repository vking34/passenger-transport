package com.hust.itss.constants;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SecureEndpoints {
    public final static String[] ACTOR_ENDPOINTS = {
            "/admin",
            "/driver",
            "/assistant",
            "/client",
            "/root"
    };
}
