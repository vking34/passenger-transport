package com.hust.itss.repositories.route;

import com.hust.itss.models.route.RouteDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public RouteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<RouteDetail> findRouteDetails(Pageable pageable) {

        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("TransportSchedule")
                .localField("schedules")
                .foreignField("_id")
                .as("transport_schedules");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, Aggregation.skip(pageable.getPageNumber() * pageable.getPageSize()), Aggregation.limit(pageable.getPageSize()));

        List<RouteDetail> routeDetails = mongoTemplate.aggregate(aggregation, "Route", RouteDetail.class).getMappedResults();
        return new PageImpl<>(routeDetails, pageable, routeDetails.size());
    }
}
