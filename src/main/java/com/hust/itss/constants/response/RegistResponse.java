package com.hust.itss.constants.response;

import com.hust.itss.models.response.Response;

public class RegistResponse {
    public static final Response INVALID_USERNAME_RESPONSE = new Response(false, 1, "username exists");
    public static final Response INVALID_PHONE_NUMBER_RESPONSE = new Response(false, 2, "phone number exists");
    public static final Response INVALID_EMAIL_RESPONSE = new Response(false, 3, "email exists");
    public static final Response SUCCESS_RESPONSE = new Response(true, null, "/");
}
