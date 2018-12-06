package com.hust.itss.repositories.transporter;

import com.hust.itss.models.route.RouteDetail;
import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SeatRepositoryImpl implements  SeatRepositoryCustom {
    private static final String ROUTE_REF = "route_ref";
    private static final String SCHEDULE_REF = "schedule_ref";
    private static final String TRANSPORTER_REF = "transporter_ref";
    private static final String DATE = "date";

    @Autowired
    private final MongoTemplate mongoTemplate;

    public SeatRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<SeatDetail> findSeatDetailsByDate(String routeRef, String scheduleRef, Date date, Pageable pageable) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("Transporter")
                .localField("transporter_ref")
                .foreignField("_id")
                .as("transporter");

        Date fromDate = new Date(date.getYear(), date.getMonth(), date.getDate());
        Date toDate = new Date(date.getYear(), date.getMonth(), date.getDate() + 1);

        MatchOperation match1 = Aggregation.match(Criteria.where(DATE).gte(fromDate).lt(toDate).and(ROUTE_REF).is(routeRef).and(SCHEDULE_REF).is(scheduleRef));

        Aggregation aggregation = Aggregation.newAggregation(match1,lookupOperation, Aggregation.skip(pageable.getPageNumber() * pageable.getPageSize()), Aggregation.limit(pageable.getPageSize()));

        List<SeatDetail> seatDetails = mongoTemplate.aggregate(aggregation, "SeatAvailability", SeatDetail.class).getMappedResults();
        return new PageImpl<>(seatDetails, pageable, seatDetails.size());
    }
}
