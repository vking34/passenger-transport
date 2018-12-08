package com.hust.itss.controllers.ticket;

import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TicketAsyncTasks {
    void insertTicket(TransportSchedule schedule, Ticket ticket, SeatDetail seatDetail, List<SeatDetail> seatDetails);
    void deleteTicket(String id);
}
