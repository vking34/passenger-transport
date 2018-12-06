package com.hust.itss.services.entity;

//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.repositories.transporter.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SeatSearch {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T00:00:00.000Z'");
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0,10);

    @Autowired
    private SeatRepository seatRepository;

    public SeatDetail searchByDate(String routeRef, String scheduleRef, Date date){
        System.out.println("route :" + routeRef);
        System.out.println("shedule: " + scheduleRef);
        Date toDate = new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
        return seatRepository.findOne(routeRef, scheduleRef, date, toDate);
    }

    public Page<SeatDetail> searchSeatDetailsByDate(String routeRef, String scheduleRef, Date date){
        Date toDate = new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
        return seatRepository.findSeatDetailsByDate(routeRef, scheduleRef, date, toDate, PAGE_REQUEST);
    }
}
