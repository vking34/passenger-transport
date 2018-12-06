package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.format.DateForm;
import com.hust.itss.models.route.Route;
import com.hust.itss.models.schedule.TransportSchedule;
//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.models.user.Employee;
import com.hust.itss.repositories.route.RouteRepository;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.repositories.transporter.TransporterRepository;
import com.hust.itss.services.entity.SeatSearch;
import com.hust.itss.utils.comparer.DateComparer;
import com.hust.itss.utils.request.PageRequestCreation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.hust.itss.constants.request.RequestParams.*;

@RestController
@RequestMapping("/api/seat-availability")
public class SeatController {
    public static final String SEAT = "available_seats";
    private static final String LATE_TIME = "23:59PM";

    private static final Page<SeatDetail> EMPTY_PAGE = new PageImpl<>(new ArrayList<SeatDetail>());

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SeatSearch seatSearch;

    @Autowired
    private TransportScheduleRepository scheduleRepository;

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private SeatAsyncTasks asyncTasks;


    @GetMapping
    Page<SeatDetail> getSeatDetails(@RequestParam(value = "route") String routeRef,
                                    @RequestParam(value = "schedule") String scheduleRef,
                                    @RequestParam(value = "date") String dateString) throws ParseException {
        System.out.println("GET SEAT");
        Page<SeatDetail> seatDetailPage;

//        Route route = routeRepository.findRouteById(routeRef);
//        if (route == null)
//            return EMPTY_PAGE;
//
//        boolean isFound = false;
//
//        for(ObjectId schedule : route.getSchedules()){
//            if (schedule.toHexString().equals(scheduleRef))
//                isFound = true;
//        }
//        if (!isFound)
//            return EMPTY_PAGE;
//
        Date date = null;
        date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);

        System.out.println(date);
//        if (!DateComparer.afterNow(date, LATE_TIME));
//            return EMPTY_PAGE;

        seatDetailPage = seatSearch.searchSeatDetailsByDate(routeRef, scheduleRef, date);
        if (seatDetailPage.getTotalElements() == 0){
            System.out.println("0");
            TransportSchedule schedule = scheduleRepository.findDetailOne(scheduleRef);
            List<SeatDetail> seatDetails = new ArrayList<>();
            List<Transporter> transporters = schedule.getTransporters();
            for(Transporter transporter : transporters){
                SeatDetail seatDetail = new SeatDetail();
                seatDetail.setDate(date);
                seatDetail.setMax(transporter.getSeaters());
                seatDetail.setAvailableSeats(transporter.getSeaters());
                seatDetail.setTransporter(transporter);
                seatDetails.add(seatDetail);
            }

            seatDetailPage = new PageImpl<>(seatDetails);
            asyncTasks.insertSeatAvailability(routeRef, scheduleRef, date, seatDetails);
        }

        return seatDetailPage;
    }

    @GetMapping("/{id}")
    SeatDetail getSeatAvailability(@PathVariable String id){
        SeatDetail seatDetail = seatRepository.findSeatDetailById(id);
        return seatDetail;
    }

    @GetMapping("/one")
    SeatDetail findByTransporterAndDate(@RequestParam(value = ROUTE) String routeRef,
                                              @RequestParam(value = SCHEDULE) String scheduleRef,
                                              @RequestParam(value = DATE) String dateString) throws ParseException {
        Date date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
        System.out.println(date);
        return seatSearch.searchByDate(routeRef, scheduleRef, date);
    }

    @GetMapping("/many")
    Page<SeatDetail> seatDetailPage (@RequestParam(value = ROUTE) String routeRef,
                                        @RequestParam(value = SCHEDULE) String scheduleRef,
                                        @RequestParam(value = DATE) String dateString) throws ParseException {
        Date date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
        System.out.println(date);
        return seatSearch.searchSeatDetailsByDate(routeRef, scheduleRef, date);
    }

}
