package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.transporter.Transporter;
import com.hust.itss.repositories.route.RouteRepository;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.transporter.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static com.hust.itss.constants.response.ErrorResponse.EXISTING_ENTITY;
import static com.hust.itss.constants.response.ErrorResponse.MISSING_FIELDS;
import static com.hust.itss.constants.response.ErrorResponse.TRANSPORTER_NOT_FOUND;

@RestController
@RequestMapping("/api/transporter")
public class TransporterController {
    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TransportScheduleRepository scheduleRepository;

    @Autowired
    private TransporterAsyncTasks transporterAsyncTasks;

    @GetMapping
    Page<Transporter> getTransporters(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "page_size", required = false) Integer pageSize,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "direct", required = false) String direct){
        return transporterRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.TRANSPORTER_PARAMS));
    }

    @GetMapping("/{id}")
    Transporter getOneTransporter(@PathVariable String id){
        Transporter transporter = transporterRepository.findTransporterById(id);
        if (transporter == null){
            return null;
        }
        transporter.setRoute(routeRepository.findRouteById(transporter.getRouteRef()));
        transporter.setSchedule(scheduleRepository.findTransportScheduleById(transporter.getScheduleRef()));
        return transporter;
    }

    @PostMapping
    Response createTransporter(@RequestBody Transporter transporter){
        String licensePlate = transporter.getLicensePlate();
        if (licensePlate == null
                || transporter.getModel() == null
                || transporter.getBranch() == null
                || transporter.getSeaters() == null)
            return MISSING_FIELDS;
        if (transporterRepository.findTransporterByLicensePlate(licensePlate) != null){
            return EXISTING_ENTITY;
        }
        transporterAsyncTasks.insertTransporter(transporter);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{id}")
    Response updateTransporter(@PathVariable String id, @RequestBody Transporter transporter){
        if (transporter.getLicensePlate() == null
                || transporter.getModel() == null
                || transporter.getBranch() == null
                || transporter.getSeaters() == null)
            return MISSING_FIELDS;

        Transporter target = transporterRepository.findTransporterById(id);
        if (target == null)
            return TRANSPORTER_NOT_FOUND;

        transporterAsyncTasks.updateTransporter(target, transporter);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteTransporter(@PathVariable String id){
        if (transporterRepository.findTransporterById(id) == null)
            return TRANSPORTER_NOT_FOUND;
        transporterAsyncTasks.deleteTransporter(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }


}
