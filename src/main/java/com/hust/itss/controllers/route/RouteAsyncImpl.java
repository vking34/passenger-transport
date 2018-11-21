package com.hust.itss.controllers.route;

import com.hust.itss.models.route.Route;
import com.hust.itss.repositories.route.RouteRepository;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteAsyncImpl implements RouteAsyncTasks {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;

    @Async
    @Override
    public void insertRoute(Route route) {
        routeRepository.insert(route);
    }

    @Async
    @Override
    public void updateRoute(Route target, Route route) {
        target.setDestination(route.getDestination());
        target.setDeparture(route.getDeparture());
        List<String> stations = route.getStations();
        if (stations != null){
            target.setStations(stations);
        }
        List<String> schedules = route.getSchedules();
        if (schedules != null){
            target.setSchedules(schedules);
        }
        routeRepository.save(target);
    }

    @Async
    @Override
    public void deleteRoute(String id) {
        routeRepository.deleteById(id);
    }

    @Async
    public void addScheduleToRoute(String scheduleId, Route route){
        System.out.println(scheduleId);
        route.getSchedules().add(scheduleId);
        routeRepository.save(route);
    }
}
