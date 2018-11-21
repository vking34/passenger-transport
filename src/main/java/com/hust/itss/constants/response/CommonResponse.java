package com.hust.itss.constants.response;

import com.hust.itss.models.response.Response;

public class CommonResponse {
    public static final Response SUCCESS_RESPONSE =  new Response(true, null, null);
    public static final Response FAIL_AUTH = new Response(false, 1, "Username/Password was wrong.");
}
