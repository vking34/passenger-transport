package com.hust.itss.controllers.ticket;


import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.responses.Response;
import com.hust.itss.models.schedules.TransportSchedule;
import com.hust.itss.models.tickets.Ticket;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.utils.PageRequestCreation;
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


    @GetMapping
    Page<Ticket> getTickets(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "page_size", required = false) Integer pageSize,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: tickets page " + page + ", page size: " + pageSize + ", sort by " + sort + ", direct " + direct);
        return ticketRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize,sort,direct, RequestParams.TICKET_PARAMS));
    }

    @PostMapping
    Response requestTicket(@RequestBody Ticket ticket){
        System.out.println("POST: requests ticket " + ticket.getClientName());
//        Ticket ticket = (Ticket) ticketForm;
        ticket.setDateCreated(new Date());
        TransportSchedule transportSchedule = transportScheduleRepository.findTransportScheduleById(ticket.getSchedule());
        ticket.setPrice(transportSchedule.getPrice());
        ticketRepository.insert(ticket);
        return new Response(true, 0, "Created the ticket successful");
    }
}
