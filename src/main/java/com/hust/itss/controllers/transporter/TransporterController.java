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

@RestController
@RequestMapping("/api/transporter")
public class TransporterController {

    private static final Response MISSING_FIELDS_RESPONSE = new Response(false, 1, "License plate/Model/Branch/Seaters is missing");
    private static final Response EXISTING_RESPONSE = new Response(false, 2, "Invalid License Plate");
    private static final Response TRANSPORTER_NOT_FOUND_RESPONSE = new Response(false, 1, "Transporter not found");

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
        System.out.println("GET: transporter page " + page + ", page size " + pageSize + ", sort by " + sort + ", direct " + direct);
        return transporterRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.TRANSPORTER_PARAMS));
    }

    @GetMapping("/{id}")
    Transporter getOneTransporter(@PathVariable String id){
        System.out.println("GET: one transporter");
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
        System.out.println("POST: create transporter");
        String licensePlate = transporter.getLicensePlate();
        if (licensePlate == null
                || transporter.getModel() == null
                || transporter.getBranch() == null
                || transporter.getSeaters() == null)
            return MISSING_FIELDS_RESPONSE;
        if (transporterRepository.findTransporterByLicensePlate(licensePlate) != null){
            return EXISTING_RESPONSE;
        }
        transporterAsyncTasks.insertTransporter(transporter);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{id}")
    Response updateTransporter(@PathVariable String id, @RequestBody Transporter transporter){
        System.out.println("POST: update transporter");
        Transporter target = transporterRepository.findTransporterById(id);
        if (target == null)
            return TRANSPORTER_NOT_FOUND_RESPONSE;
        if (transporter.getLicensePlate() == null
                || transporter.getModel() == null
                || transporter.getBranch() == null
                || transporter.getSeaters() == null)
            return MISSING_FIELDS_RESPONSE;
        System.out.println("saving...");
        transporterAsyncTasks.updateTransporter(target, transporter);
        System.out.println("return resp");
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteTransporter(@PathVariable String id){
        System.out.println("DELETE: delete transporter " + id);
        if (transporterRepository.findTransporterById(id) == null)
            return TRANSPORTER_NOT_FOUND_RESPONSE;
        transporterAsyncTasks.deleteTransporter(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }


}
