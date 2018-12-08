package com.hust.itss.controllers.route;

import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.models.request.IdentForm;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.route.Route;
import com.hust.itss.models.route.RouteDetail;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.route.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private static final Response ROUTE_NOT_FOUND_RESPONSE = new Response(false, 1, "Route not found");
    private static final Response MISSING_FIELDS_RESPONSE = new Response(false, 2, "Departure/Destination field is missing");
    private static final Response SCHEDULE_NOT_FOUND_RESPONSE = new Response(false, 2, "Schedule not found");

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;

    @Autowired
    private RouteAsyncTasks asyncTasks;

    @GetMapping
    Page<Route> getRoutes(@RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "page_size", required = false) Integer pageSize,
                          @RequestParam(value = "search", required = false) String searchString,
                          @RequestParam(value = "sort", required = false) String sort,
                          @RequestParam(value = "direct", required = false) String direct){
        if (searchString != null)
            return routeRepository.searchRoute(searchString, PageRequestCreation.getSimplePageRequest(page, pageSize));
        return routeRepository.findAll(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.ROUTE_PARAMS));
    }

    @GetMapping("/details")
    Page<RouteDetail> getRouteDetails(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "page_size", required = false) Integer pageSize){
        System.out.println("GET ROUTE");
        if (page == null)
            page = 1;
        if (pageSize == null)
            pageSize = 10;
        return routeRepository.findRouteDetails(PageRequest.of(page - 1,pageSize));
    }

    @GetMapping("/{id}")
    Route getRoute(@PathVariable String id){
        Route route = routeRepository.findRouteById(id);
        System.out.println(route.getSchedules());
        return route;
    }

    @PostMapping
    Response createRoute(@RequestBody Route route){
        if(route.getDeparture() == null || route.getDestination() == null){
            return MISSING_FIELDS_RESPONSE;
        }

        if(route.getSchedules() == null){
            route.setSchedules(new ArrayList<>());
        }
        asyncTasks.insertRoute(route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{id}")
    Response updateRoute(@PathVariable String id, @RequestBody Route route){
        Route target = routeRepository.findRouteById(id);
        if (target == null)
            return ROUTE_NOT_FOUND_RESPONSE;
        if (route.getDeparture() == null || route.getDestination() == null)
            return MISSING_FIELDS_RESPONSE;
        asyncTasks.updateRoute(target, route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{routeId}/schedule")
    Response addScheduleToRoute(@PathVariable String routeId, @RequestBody IdentForm identForm){
        Route route = routeRepository.findRouteById(routeId);
        if(route == null)
            return ROUTE_NOT_FOUND_RESPONSE;

        String scheduleId = identForm.getId();
        if(scheduleId == null || transportScheduleRepository.findTransportScheduleById(scheduleId) == null){
            return SCHEDULE_NOT_FOUND_RESPONSE;
        }
        asyncTasks.addScheduleToRoute(scheduleId, route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteRoute(@PathVariable String id){
        Route route = routeRepository.findRouteById(id);
        if (route == null)
            return ROUTE_NOT_FOUND_RESPONSE;
        asyncTasks.deleteRoute(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }
}
