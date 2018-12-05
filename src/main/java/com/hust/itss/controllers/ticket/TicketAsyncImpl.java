package com.hust.itss.controllers.ticket;

import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.repositories.ticket.TicketRepository;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.repositories.transporter.TransporterRepository;
import com.hust.itss.services.entity.SeatSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TicketAsyncImpl implements TicketAsyncTasks {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private SeatSearch seatSearch;

    @Override
    public void insertRoute(String routeRef, TransportSchedule schedule, String transporterRef, Ticket ticket) {
        ticket.setDateCreated(new Date());
        ticket.setPrice(schedule.getPrice());
        ticketRepository.insert(ticket);
//        SeatAvailability seatAvailability = seatRepository.find
//        Transporter transporter = transporterRepository.findTransporterById(transporterRef);
    }

    @Async
    @Override
    public void deleteRoute(String id) {
        ticketRepository.deleteById(id);
    }
}
