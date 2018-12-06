package com.hust.itss.controllers.transporter;

import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.repositories.transporter.SeatRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SeatAsyncImpl implements SeatAsyncTasks {

    @Autowired
    private SeatRepository seatRepository;

    @Async
    public void insertSeatAvailability(String routeRef, String scheduleRef, Date date, List<Transporter> transporters, SeatDetail seatDetail) {
        System.out.println("insert seat...");
        seatDetail.setRouteRef(routeRef);
        seatDetail.setScheduleRef(scheduleRef);
        seatDetail.setDate(new Date(date.getYear(), date.getMonth(), date.getDate()+ 1));
        List<ObjectId> transporterRef = new ArrayList<>();
        transporters.forEach(item -> {
            transporterRef.add(new ObjectId(item.getId()));
        });
        seatDetail.setTransporterRef(transporterRef);
        seatRepository.insert((SeatAvailability) seatDetail);
    }
}
