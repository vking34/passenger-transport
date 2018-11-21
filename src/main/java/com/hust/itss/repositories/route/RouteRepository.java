package com.hust.itss.repositories.route;

import com.hust.itss.models.route.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends MongoRepository<Route, String>, RouteRepositoryCustom{
    public Route findRouteById(String id);
}
