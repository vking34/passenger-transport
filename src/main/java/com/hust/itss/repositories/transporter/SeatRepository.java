package com.hust.itss.repositories.transporter;

//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface SeatRepository extends MongoRepository<SeatDetail, String>, SeatRepositoryCustom {

    SeatDetail findSeatDetailById(String id);

//    Page<SeatAvailability> findSeatAvailabilityByRouteRefAndScheduleRef(String routeRef, String scheduleRef, Pageable pageable);
//
//    @Query("{ route_ref : ?0, schedule_ref : ?1, date : { $gte : ?2 } }")
//    Page<SeatAvailability> findSeatAvailabilityByDate(String routeRef, String scheduleRef, Date date, Pageable pageable);
//
//    @Query("{ route_ref : ?0, schedule_ref : ?1, transporter_ref : ?2, date : { $gte : ?3 } }")
//    Page<SeatAvailability> findSeatAvailabilityByDateAndTransporter(String routeRef, String scheduleRef, String transporterRef, Date date, Pageable pageable);

    @Query("{ route_ref : ?0, schedule_ref : ?1, date : { $gte : ?2 , $lte : ?3 }}")
    List<SeatDetail> findOne(String routeRef, String scheduleRef, Date fromDate, Date toDate);

    @Query("{ route_ref : ?0, schedule_ref : ?1, date : { $gte : ?2 , $lte : ?3 }}")
    Page<SeatDetail> findSeatDetailsByDate(String routeRef, String scheduleRef, Date fromDate, Date toDate, Pageable pageable);


}
