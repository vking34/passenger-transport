package com.hust.itss.controllers.route;

import com.hust.itss.models.route.Route;
import org.springframework.stereotype.Component;

@Component
public interface RouteAsyncTasks {
    public void insertRoute(Route route);
    public void updateRoute(Route target, Route route);
    public void deleteRoute(String id);
    public void addScheduleToRoute(String scheduleId, Route route);
}
