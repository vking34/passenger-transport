package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.format.DateForm;
import com.hust.itss.models.route.Route;
import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.transporter.SeatAvailability;
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

    private Page<SeatDetail> seatDetailPage;
    private SeatDetail seatDetail = new SeatDetail();

    @GetMapping
    Page<SeatDetail> getSeatDetails(@RequestParam(value = "route") String routeRef,
                                    @RequestParam(value = "schedule") String scheduleRef,
                                    @RequestParam(value = "date") String dateString,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "page_size", required = false) Integer pageSize){
        Route route = routeRepository.findRouteById(routeRef);
        if (route == null)
            return EMPTY_PAGE;

        boolean isFound = false;

        for(ObjectId schedule : route.getSchedules()){
            if (schedule.toHexString().equals(scheduleRef))
                isFound = true;
        }
        if (!isFound)
            return EMPTY_PAGE;

        PageRequest pageRequest = PageRequestCreation.getBasicPageRequest(page, pageSize, null, null);
        Date date = null;
        if (dateString != null) {
            try {
                date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                return EMPTY_PAGE;
            }
        }
//        if (!DateComparer.afterNow(date, LATE_TIME));
//            return EMPTY_PAGE;

        seatDetailPage = seatRepository.findSeatDetailsByDate(routeRef, scheduleRef, date ,pageRequest);
        if (seatDetailPage.getTotalElements() == 0){
            TransportSchedule schedule = scheduleRepository.findDetailOne(scheduleRef);
            System.out.println(schedule.getPrice());
            List<Transporter> transporters = schedule.getTransporters();

            Transporter transporter = transporters.get(0);

            seatDetail.setMax(transporter.getSeaters());
            seatDetail.setAvailableSeats(transporter.getSeaters());
            seatDetail.setTransporter(transporters);
            seatDetailPage = new PageImpl<>(new ArrayList<>(Arrays.asList(seatDetail)));
        }

        return seatDetailPage;
    }

    @GetMapping("/{id}")
    SeatAvailability getSeatAvailability(@PathVariable String id){
        SeatAvailability seatAvailability = seatRepository.findSeatAvailabilityById(id);
        Date date = seatAvailability.getDate();
        System.out.println(date.getYear() + ", " + date.getMonth() + ", " + date.getDate());
        return seatAvailability;
    }

    @GetMapping("/one")
    SeatAvailability findByTransporterAndDate(@RequestParam(value = ROUTE) String routeRef,
                                              @RequestParam(value = SCHEDULE) String scheduleRef,
                                              @RequestParam(value = DATE) String dateString) throws ParseException {
        Date date = DateForm.SIMPLE_DATE_FORMAT.parse(dateString);
        System.out.println(date);
        return seatSearch.searchByDate(routeRef, scheduleRef, date);
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
