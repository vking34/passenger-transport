package com.hust.itss.controllers.ticket;


import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.tickets.Ticket;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    Page<Ticket> getTickets(@RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "page_size", required = false) Integer pageSize,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: tickets page " + page + ", page size: " + pageSize + ", sort by " + sort + ", direct " + direct);
        return ticketRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize,sort,direct, RequestParams.TICKET_PARAMS));
    }
}
