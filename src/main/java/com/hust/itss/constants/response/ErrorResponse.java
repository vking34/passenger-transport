package com.hust.itss.constants.response;

import com.hust.itss.models.response.Response;

public class ErrorResponse {
    public static final Response EMPTY_BODY = new Response(false, 101, null);
    public static final Response WRONG_ID = new Response(false, 102, null);
    public static final Response MISSING_FIELDS = new Response(false, 103, "Missing Fields");
    public static final Response EXISTING_ENTITY = new Response(false, 104, null);
    public static final Response REQUIRED_JSON = new Response(false, 301, null);

    // Ticket
    public static final Response TICKET_NOT_FOUND = new Response(false, 501, "Ticket not found");
    public static final Response ROUTE_NOT_FOUND = new Response(false, 503, "Route not found");
    public static final Response SCHEDULE_NOT_FOUND = new Response(false, 504, "Schedule not found");
    public static final Response TRANSPORTER_NOT_FOUND = new Response(false, 505, "Transporter not found");
    public static final Response INVALID_TIME = new Response(false, 506, "Invalid booking time");


}
