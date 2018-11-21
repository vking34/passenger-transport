package com.hust.itss.controllers.ticket;

import com.hust.itss.models.ticket.Ticket;
import org.springframework.stereotype.Component;

@Component
public interface TicketAsyncTasks {
    void insertRoute(Ticket ticket);
    void deleteRoute(String id);
}
