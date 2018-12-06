package com.hust.itss.controllers.ticket;

import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.stereotype.Component;

@Component
public interface TicketAsyncTasks {
    void insertTicket(String routeRef, TransportSchedule schedule, String transporterRef, Ticket ticket, SeatDetail seatDetail);
    void deleteTicket(String id);
}
