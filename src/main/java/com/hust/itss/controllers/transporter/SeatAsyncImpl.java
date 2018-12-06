package com.hust.itss.controllers.transporter;

//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.repositories.transporter.SeatRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SeatAsyncImpl implements SeatAsyncTasks {

    @Autowired
    private SeatRepository seatRepository;

    @Async
    @Override
    public void insertSeatAvailability(String routeRef, String scheduleRef, Date date, List<SeatDetail> seatDetails) {
        System.out.println("insert seat...");
        for(SeatDetail seatDetail : seatDetails){
            seatDetail.setDate(new Date(date.getYear(), date.getMonth(), date.getDate()+1, 0, 0,0));
            seatDetail.setRouteRef(routeRef);
            seatDetail.setScheduleRef(scheduleRef);
            seatRepository.insert(seatDetail);
        }
    }
}
