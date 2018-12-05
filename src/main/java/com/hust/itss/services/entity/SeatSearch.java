package com.hust.itss.services.entity;

import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.repositories.transporter.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SeatSearch {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T00:00:00.000Z'");

    @Autowired
    private SeatRepository seatRepository;

    public SeatAvailability searchByDate(String routeRef, String scheduleRef, Date date){
//        String fromDate = DATE_FORMAT.format(date);
//        String toDate = DATE_FORMAT.format();
        Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
        return seatRepository.findOne(routeRef, scheduleRef, date, date2);
    }
}
