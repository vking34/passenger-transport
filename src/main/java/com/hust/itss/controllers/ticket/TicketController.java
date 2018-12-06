package com.hust.itss.controllers.ticket;

import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.route.Route;
import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.repositories.route.RouteRepository;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.services.entity.SeatSearch;
import com.hust.itss.utils.comparer.DateComparer;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.ticket.TicketRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

import static com.hust.itss.constants.request.RequestParams.PAGE;
import static com.hust.itss.constants.request.RequestParams.PAGE_SIZE;
import static com.hust.itss.constants.request.RequestParams.SORT;
import static com.hust.itss.constants.request.RequestParams.DIRECT;
import static com.hust.itss.constants.response.ErrorResponse.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;

    @Autowired
    private SeatSearch seatSearch;

    @Autowired
    private TicketAsyncTasks asyncTasks;

    @GetMapping
    Page<Ticket> getTickets(@RequestParam(value = PAGE, required = false) Integer page,
                            @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize,
                            @RequestParam(value = SORT, required = false) String sort,
                            @RequestParam(value = DIRECT, required = false) String direct){
        return ticketRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize,sort,direct, RequestParams.TICKET_PARAMS));
    }

    @PostMapping
    Response requestTicket(@RequestBody Ticket ticket){
        String routeRef = ticket.getRoute();
        String scheduleRef = ticket.getSchedule();
        String transporterRef = ticket.getTransporter();
        String phoneNumber = ticket.getPhoneNumber();
        Date reservationDate = ticket.getReservationDate();

        if (routeRef == null || scheduleRef == null ||transporterRef == null || phoneNumber == null || reservationDate == null)
            return MISSING_FIELDS;

        Route route = routeRepository.findRouteById(routeRef);
        if (route == null)
            return ROUTE_NOT_FOUND;

        boolean isFound = false;
        System.out.println(route.getSchedules());
        for(ObjectId schedule : route.getSchedules()){
            if (schedule.toHexString().equals(scheduleRef))
                isFound = true;
        }
        if (!isFound)
            return SCHEDULE_NOT_FOUND;
        isFound = false;
        TransportSchedule schedule = transportScheduleRepository.findTransportScheduleById(scheduleRef);
        for(ObjectId transporter: schedule.getTransporterRefs()){
            if (transporter.toHexString().equals(transporterRef)){
                isFound = true;
            }
        }
        if (!isFound){
            return TRANSPORTER_NOT_FOUND;
        }
        System.out.println(reservationDate);

        if (!DateComparer.afterNow(reservationDate, schedule.getStartingTime()))
            return INVALID_TIME;

        SeatDetail seatDetail = seatSearch.searchByDate(routeRef,scheduleRef, reservationDate);
        if (seatDetail.getAvailableSeats() < 1)
            return FULL_TRANSPORTER;

        asyncTasks.insertTicket(routeRef, schedule, transporterRef,ticket, seatDetail);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping
    Response deleteTicket(@RequestParam String id){
        Ticket ticket = ticketRepository.findTicketById(id);
        if (ticket == null)
            return TICKET_NOT_FOUND;
        asyncTasks.deleteTicket(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }

}
