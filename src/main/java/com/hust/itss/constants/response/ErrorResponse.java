package com.hust.itss.constants.response;

import com.hust.itss.models.response.Response;

public class ErrorResponse {
    public static final Response EMPTY_BODY = new Response(false, 101, null);
    public static final Response WRONG_ID = new Response(false, 102, null);
    public static final Response MISSING_FIELDS = new Response(false, 103, null);
    public static final Response EXISTING_ENTITY = new Response(false, 104, null);
}
