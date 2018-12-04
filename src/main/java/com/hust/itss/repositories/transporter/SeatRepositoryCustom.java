package com.hust.itss.repositories.transporter;

import com.hust.itss.models.transporter.SeatDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface SeatRepositoryCustom {
    public Page<SeatDetail> findSeatDetails(String routeRef, String scheduleRef, Date date, Pageable pageable);
}
