package com.hust.itss.controllers.transporter;

import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface SeatAsyncTasks {
    public void insertSeatAvailability(String routeRef, String scheduleRef, Date date, List<SeatDetail> seatDetails);
}
