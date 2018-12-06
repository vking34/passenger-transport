package com.hust.itss.repositories.schedule;

import com.hust.itss.models.schedule.TransportSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

public class TransportScheduleRepositoryImpl implements TransportScheduleRepositoryCustom {

    private static final String TRANSPORT_SCHEDULE = "TransportSchedule";

    @Autowired
    private final MongoTemplate mongoTemplate;

    public TransportScheduleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public TransportSchedule findDetailOne(String id) {
//        System.out.println("ID: " + id);
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("Transporter")
                .localField("transporter_refs")
                .foreignField("_id")
                .as("transporters");

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("_id").is(id)), lookupOperation);
        return mongoTemplate.aggregate(aggregation, TRANSPORT_SCHEDULE , TransportSchedule.class).getUniqueMappedResult();
    }
}
