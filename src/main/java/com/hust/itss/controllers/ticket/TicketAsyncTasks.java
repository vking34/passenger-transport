package com.hust.itss.controllers.ticket;

import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
import org.springframework.stereotype.Component;

@Component
public interface TicketAsyncTasks {
    void insertRoute(String routeRef, TransportSchedule schedule, String transporterRef, Ticket ticket);
    void deleteRoute(String id);
}
