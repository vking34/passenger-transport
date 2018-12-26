package com.hust.itss.constants.response;

import com.hust.itss.models.response.Response;

public class ErrorResponse {
    public static final Response EMPTY_BODY = new Response(false, 101, null);
    public static final Response WRONG_ID = new Response(false, 102, null);
    public static final Response MISSING_FIELDS = new Response(false, 103, "Missing Fields");
    public static final Response EXISTING_ENTITY = new Response(false, 104, null);
    public static final Response REQUIRED_JSON = new Response(false, 105, null);

    // Route
    public static final Response ROUTE_NOT_FOUND = new Response(false, 201, "Route not found");

    // Schedule
    public static final Response SCHEDULE_NOT_FOUND = new Response(false, 301, "Schedule not found");

    // Transporter
    public static final Response TRANSPORTER_NOT_FOUND = new Response(false, 401, "Transporter not found");

    // Ticket
    public static final Response TICKET_NOT_FOUND = new Response(false, 501, "Ticket not found");
    public static final Response INVALID_TIME = new Response(false, 506, "Invalid booking time");
    public static final Response NOT_ENOUGH_SEATS = new Response(false, 507, "Not enough seats");

    // Schedule
    public static final Response TRANSPORT_SCHEDULE_NOT_FOUND = new Response(false, 601, "Transport Schedule not found");

}
