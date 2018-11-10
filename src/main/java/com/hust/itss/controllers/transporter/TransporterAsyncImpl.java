package com.hust.itss.controllers.transporter;

import com.hust.itss.models.routes.Route;
import com.hust.itss.models.schedules.TransportSchedule;
import com.hust.itss.models.transporters.Transporter;
import com.hust.itss.repositories.route.RouteRepository;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.repositories.transporter.TransporterRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransporterAsyncImpl implements TransporterAsyncTasks {

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private TransportScheduleRepository scheduleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Async
    public void insertTransporter(Transporter transporter){
        transporter.setStatus(0);
        TransportSchedule schedule = scheduleRepository.findTransportScheduleById(transporter.getScheduleRef());
        Route route = routeRepository.findRouteById(transporter.getRouteRef());
        if (schedule == null)
            transporter.setScheduleRef(null);
        if (route == null)
            transporter.setRouteRef(null);
        Transporter result = transporterRepository.insert(transporter);
        if (schedule != null){
            schedule.getTransporterRefs().add(new ObjectId(result.getId()));
            scheduleRepository.save(schedule);
        }
    }

    @Async
    public void updateTransporter(Transporter target, Transporter transporter) {
        target.setLicensePlate(transporter.getLicensePlate());
        target.setModel(transporter.getModel());
        target.setBranch(transporter.getBranch());
        target.setSeaters(transporter.getSeaters());
        target.setStatus(transporter.getStatus());
        Route route = routeRepository.findRouteById(transporter.getRouteRef());
        if (route != null)
            target.setRouteRef(transporter.getRouteRef());

        String newScheduleRef = transporter.getScheduleRef();
        String oldScheduleRef = target.getScheduleRef();
        TransportSchedule schedule = scheduleRepository.findTransportScheduleById(newScheduleRef);

        if (schedule != null){
            if (oldScheduleRef != null) {
                if(!oldScheduleRef.equals(newScheduleRef)) {
                    ObjectId targetId = new ObjectId(target.getId());
                    schedule.getTransporterRefs().add(targetId);
                    scheduleRepository.save(schedule);
                    schedule = scheduleRepository.findTransportScheduleById(oldScheduleRef);
                    if (schedule != null) {
                        List<ObjectId> transporters = schedule.getTransporterRefs();
                        ObjectId transporterRef;
                        for (int i = 0; i < transporters.size(); i++) {
                            transporterRef = transporters.get(i);
                            if (transporterRef.equals(targetId)) {
                                transporters.remove(i);
                                schedule.setTransporterRefs(transporters);
                                scheduleRepository.save(schedule);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                target.setScheduleRef(newScheduleRef);
            }
        }
        System.out.println("saved");
        transporterRepository.save(target);
    }

    @Async
    public void deleteTransporter(String id){
        transporterRepository.deleteById(id);
    }
}
