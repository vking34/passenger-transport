package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.format.DateForm;
import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.utils.request.PageRequestCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/seat-availability")
public class SeatController {
    public static final String SEAT = "available_seats";

    @Autowired
    private SeatRepository seatRepository;

    @GetMapping
    Page<SeatDetail> getSeatDetails(@RequestParam(value = "route") String routeRef,
                                    @RequestParam(value = "schedule") String scheduleRef,
                                    @RequestParam(value = "date") String dateString,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "page_size", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequestCreation.getBasicPageRequest(page, pageSize, null, null);
        Date date = null;
        if (dateString != null) {
            try {
                date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                date = null;
            }
        }
        return seatRepository.findSeatDetails(routeRef, scheduleRef, date ,pageRequest);
    }

    @GetMapping("/{id}")
    SeatAvailability getSeatAvailability(@PathVariable String id){
        SeatAvailability seatAvailability = seatRepository.findSeatAvailabilityById(id);
        Date date = seatAvailability.getDate();
        System.out.println(date.getYear() + ", " + date.getMonth() + ", " + date.getDate());
        return seatAvailability;
    }

//    @GetMapping
//    Page<SeatAvailability> getSeatAvailability(@RequestParam(value = "route") String routeRef,
//                                         @RequestParam(value = "schedule") String scheduleRef,
//                                         @RequestParam(value = "date", required = false) String dateString,
//                                         @RequestParam(value = "transporter", required = false) String transporterRef,
//                                         @RequestParam(value = "page", required = false) Integer page,
//                                         @RequestParam(value = "page_size", required = false) Integer pageSize,
//                                         @RequestParam(value = "sort", required = false) String direct
//                                               ) throws ParseException {
//        System.out.println("GET seats");
//        PageRequest pageRequest;
//        pageRequest = PageRequestCreation.getBasicPageRequest(page, pageSize, SEAT, direct);
//        if (dateString == null){
//            return seatRepository.findSeatAvailabilityByRouteRefAndScheduleRef(routeRef, scheduleRef, pageRequest);
//        }
//        Date date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
//        if (transporterRef == null)
//        {
//            return seatRepository.findSeatAvailabilityByDate(routeRef, scheduleRef, date, pageRequest);
//        }
//        Page<SeatAvailability> seatAvailabilities = seatRepository.findSeatAvailabilityByDateAndTransporter(routeRef, scheduleRef, transporterRef, date, pageRequest);
//        return seatAvailabilities;
//    }
}
