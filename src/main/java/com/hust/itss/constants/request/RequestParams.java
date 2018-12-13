package com.hust.itss.constants.request;

//import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Repository
public class RequestParams {
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "page_size";
    public static final String SORT = "sort";
    public static final String DIRECT = "direct";
    public static final String SEARCH = "search";       // string search

//    public static List<String> DIRECTS = new ArrayList<>(Arrays.asList("ASC", "DESC"));
    public static final List<String> TICKET_PARAMS = new ArrayList<>(Arrays.asList("booking_date", "date", "price"));
    public static final List<String> USER_PARAMS = new ArrayList<>(Arrays.asList("name", "id"));
    public static final List<String> CLIENT_PARAMS = new ArrayList<>(Arrays.asList("name", "id", "point"));
    public static final List<String> TRANSPORTER_PARAMS = new ArrayList<>(Arrays.asList("license_plate", "model", "branch", "seats", "status"));
    public static final List<String> ROUTE_PARAMS = new ArrayList<>(Arrays.asList("destination", "departure"));
    public static final List<String> WORK_SCHEDULE_PARAMS = new ArrayList<>(Arrays.asList("starting_time", "ending_time"));
    public static final List<String> TRANSPORT_SCHEDULE_PARAMS = new ArrayList<>(Arrays.asList("starting_time", "ending_time", "price"));

    //
    public static final String TRANSPORTER = "transporter";
    public static final String DATE = "date";
    public static final String ROUTE = "route";
    public static final String SCHEDULE = "schedule";
}
