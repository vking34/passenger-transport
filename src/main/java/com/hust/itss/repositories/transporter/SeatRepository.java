package com.hust.itss.repositories.transporter;

import com.hust.itss.models.transporter.SeatAvailability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface SeatRepository extends MongoRepository<SeatAvailability, String>, SeatRepositoryCustom {
    SeatAvailability findSeatAvailabilityById(String id);

    Page<SeatAvailability> findSeatAvailabilityByRouteRefAndScheduleRef(String routeRef, String scheduleRef, Pageable pageable);

    @Query("{ route_ref : ?0, schedule_ref : ?1, date : { $gte : ?2 } }")
    Page<SeatAvailability> findSeatAvailabilityByDate(String routeRef, String scheduleRef, Date date, Pageable pageable);

    @Query("{ route_ref : ?0, schedule_ref : ?1, transporter_ref : ?2, date : { $gte : ?3 } }")
    Page<SeatAvailability> findSeatAvailabilityByDateAndTransporter(String routeRef, String scheduleRef, String transporterRef, Date date, Pageable pageable);
}
