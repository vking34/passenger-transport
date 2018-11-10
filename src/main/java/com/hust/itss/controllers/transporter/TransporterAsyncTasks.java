package com.hust.itss.controllers.transporter;

import com.hust.itss.models.transporters.Transporter;
import org.springframework.stereotype.Component;

@Component
public interface TransporterAsyncTasks {
    public void insertTransporter(Transporter transporter);
    public void updateTransporter(Transporter target, Transporter transporter);
    public void deleteTransporter(String id);
}
