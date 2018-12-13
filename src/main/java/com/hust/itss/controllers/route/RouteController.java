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
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import static com.hust.itss.constants.request.RequestParams.*;
import static com.hust.itss.constants.response.ErrorResponse.MISSING_FIELDS;
import static com.hust.itss.constants.response.ErrorResponse.ROUTE_NOT_FOUND;
import static com.hust.itss.constants.response.ErrorResponse.SCHEDULE_NOT_FOUND;

@RestController
@RequestMapping("/api/route")
public class RouteController {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;

    @Autowired
    private RouteAsyncTasks asyncTasks;

    @GetMapping
    Page<Route> getRoutes(@RequestParam(value = PAGE, required = false) Integer page,
                          @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize,
                          @RequestParam(value = SEARCH, required = false) String searchString,
                          @RequestParam(value = SORT, required = false) String sort,
                          @RequestParam(value = DIRECT, required = false) String direct){
        if (searchString != null)
            return routeRepository.searchRoute(searchString, PageRequestCreation.getSimplePageRequest(page, pageSize));
        return routeRepository.findAll(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.ROUTE_PARAMS));
    }

    @GetMapping("/details")
    Page<RouteDetail> getRouteDetails(@RequestParam(value = PAGE, required = false) Integer page,
                                      @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize){
        return routeRepository.findRouteDetails(PageRequestCreation.getSimplePageRequest(page, pageSize));
    }

    @PostMapping
    Response createRoute(@RequestBody Route route){
        if(route.getDeparture() == null || route.getDestination() == null){
            return MISSING_FIELDS;
        }
        if(route.getSchedules() == null){
            route.setSchedules(new ArrayList<>());
        }
        asyncTasks.insertRoute(route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @GetMapping("/{id}")
    Route getRoute(@PathVariable String id){
        Route route = routeRepository.findRouteById(id);
        return route;
    }

    @PostMapping("/{id}")
    Response updateRoute(@PathVariable String id, @RequestBody Route route){
        Route target = routeRepository.findRouteById(id);
        if (target == null)
            return ROUTE_NOT_FOUND;
        if (route.getDeparture() == null || route.getDestination() == null)
            return MISSING_FIELDS;
        asyncTasks.updateRoute(target, route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{routeId}/schedule")
    Response addScheduleToRoute(@PathVariable String routeId,
                                @RequestBody IdentForm identForm){
        Route route = routeRepository.findRouteById(routeId);
        if(route == null)
            return ROUTE_NOT_FOUND;

        String scheduleId = identForm.getId();
        if(scheduleId == null || transportScheduleRepository.findTransportScheduleById(scheduleId) == null){
            return SCHEDULE_NOT_FOUND;
        }
        asyncTasks.addScheduleToRoute(scheduleId, route);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteRoute(@PathVariable String id){
        Route route = routeRepository.findRouteById(id);
        if (route == null)
            return ROUTE_NOT_FOUND;
        asyncTasks.deleteRoute(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }
}
