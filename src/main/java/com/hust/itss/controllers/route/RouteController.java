package com.hust.itss.controllers.route;

import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.routes.Route;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    Page<Route> getRoutes(@RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "page_size", required = false) Integer pageSize,
                          @RequestParam(value = "sort", required = false) String sort,
                          @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: routes page " + page + ", page size: " + pageSize + ", sort by " + sort + ", direct " + direct);
        return routeRepository.findAll(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.ROUTE_PARAMS));
    }



}
