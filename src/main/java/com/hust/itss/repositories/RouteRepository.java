package com.hust.itss.repositories;

import com.hust.itss.models.routes.Route;
import com.hust.itss.models.routes.RouteDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String>, RouteRepositoryCustom{

}
