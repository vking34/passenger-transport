package com.hust.itss.repositories.route;

import com.hust.itss.models.route.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends MongoRepository<Route, String>, RouteRepositoryCustom{
    public Route findRouteById(String id);

    @Query("{ $or : [ { departure : { '$regex' : ?0 , '$options': 'i' } }, { destination : { '$regex' : ?0 , '$options': 'i' } } ] }")
    public Page<Route> searchRoute(String searchString, Pageable pageable);
}
