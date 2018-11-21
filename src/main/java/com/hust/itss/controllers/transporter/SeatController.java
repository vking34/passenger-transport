package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.format.DateForm;
import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.utils.request.PageRequestCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;


@RestController
@RequestMapping("/api/seat-availability")
public class SeatController {
    public static final String SEAT = "available_seats";

    @Autowired
    private SeatRepository seatRepository;

    @GetMapping
    Page<SeatAvailability> getSeatAvailability(@RequestParam(value = "route") String routeRef,
                                               @RequestParam(value = "schedule") String scheduleRef,
                                               @RequestParam(value = "date", required = false) String dateString,
                                               @RequestParam(value = "transporter", required = false) String transporterRef,
                                               @RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "page_size", required = false) Integer pageSize,
                                               @RequestParam(value = "sort", required = false) String direct
                                               ) throws ParseException {
        PageRequest pageRequest;
        pageRequest = PageRequestCreation.getBasicPageRequest(page, pageSize, SEAT, direct);
        if (dateString == null){
            return seatRepository.findSeatAvailabilityByRouteRefAndScheduleRef(routeRef, scheduleRef, pageRequest);
        }
        Date date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
        if (transporterRef == null)
        {
            return seatRepository.findSeatAvailabilityByDate(routeRef, scheduleRef, date, pageRequest);
        }
        return seatRepository.findSeatAvailabilityByDateAndTransporter(routeRef, scheduleRef, transporterRef, date, pageRequest);
    }


}
