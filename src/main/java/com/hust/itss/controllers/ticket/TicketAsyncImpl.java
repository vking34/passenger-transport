package com.hust.itss.controllers.ticket;

import com.hust.itss.models.tickets.Ticket;
import com.hust.itss.repositories.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TicketAsyncImpl implements TicketAsyncTasks {

    @Autowired
    private TicketRepository ticketRepository;

    @Async
    @Override
    public void insertRoute(Ticket ticket) {
        ticketRepository.insert(ticket);
    }

    @Override
    public void deleteRoute(String id) {
        ticketRepository.deleteById(id);
    }
}
