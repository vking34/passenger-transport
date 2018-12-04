package com.hust.itss.controllers.ticket;


import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;

    @Autowired
    private TicketAsyncTasks asyncTasks;

    private static final Response NOT_FOUND_TICKET = new Response(false, 1, "ticket not found");

    @GetMapping
    Page<Ticket> getTickets(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "page_size", required = false) Integer pageSize,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "direct", required = false) String direct){
        return ticketRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize,sort,direct, RequestParams.TICKET_PARAMS));
    }

    @PostMapping
    Response requestTicket(@RequestBody Ticket ticket){
//        Ticket ticket = (Ticket) ticketForm;
        ticket.setDateCreated(new Date());
        TransportSchedule transportSchedule = transportScheduleRepository.findTransportScheduleById(ticket.getSchedule());
        ticket.setPrice(transportSchedule.getPrice());
        asyncTasks.insertRoute(ticket);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping
    Response deleteTicket(@RequestParam String id){
        Ticket ticket = ticketRepository.findTicketById(id);
        if (ticket == null)
            return NOT_FOUND_TICKET;
        asyncTasks.deleteRoute(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }

}
