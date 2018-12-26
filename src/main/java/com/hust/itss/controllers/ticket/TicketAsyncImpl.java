package com.hust.itss.controllers.ticket;

import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.models.ticket.Ticket;
//import com.hust.itss.models.transporter.SeatAvailability;
import com.hust.itss.models.transporter.SeatDetail;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.repositories.ticket.TicketRepository;
import com.hust.itss.repositories.transporter.SeatRepository;
import com.hust.itss.repositories.transporter.TransporterRepository;
import com.hust.itss.services.entity.SeatSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TicketAsyncImpl implements TicketAsyncTasks {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatSearch seatSearch;

    @Async
    @Override
    public void createTicket(TransportSchedule schedule, Ticket ticket, SeatDetail seatDetail, List<SeatDetail> seatDetails) {
        Date reservationDate = ticket.getReservationDate();
        ticket.setReservationDate(new Date(reservationDate.getYear(), reservationDate.getMonth(), reservationDate.getDate() + 1));
        ticket.setDateCreated(new Date());
        ticket.setPrice(schedule.getPrice() * ticket.getTicketQuantity());
        ticketRepository.insert(ticket);
        seatDetail.setAvailableSeats(seatDetail.getAvailableSeats() - ticket.getTicketQuantity());
        seatRepository.save(seatDetail);
        seatDetails.remove(0);
        for(SeatDetail i: seatDetails){
            seatRepository.delete(i);
        }
    }

    @Async
    @Override
    public void deleteTicket(String id) {
        ticketRepository.deleteById(id);
    }
}
