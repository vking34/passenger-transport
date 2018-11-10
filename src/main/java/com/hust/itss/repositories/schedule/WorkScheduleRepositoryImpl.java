package com.hust.itss.repositories.schedule;

import com.hust.itss.models.schedules.WorkSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

public class WorkScheduleRepositoryImpl implements WorkScheduleRepositoryCustom {
    private static final String WORK_SCHEDULE = "WorkSchedule";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public WorkSchedule findDetailOne(String id) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("Transporter")
                .localField("transporter_ref")
                .foreignField("_id")
                .as("transporter");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("_id").is(id)), lookupOperation);
        return mongoTemplate.aggregate(aggregation, WORK_SCHEDULE, WorkSchedule.class).getUniqueMappedResult();
    }
}
