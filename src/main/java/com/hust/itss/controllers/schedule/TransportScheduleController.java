package com.hust.itss.controllers.schedule;

import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.schedule.TransportSchedule;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.schedule.TransportScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transport-schedule")
public class TransportScheduleController {

    private static final Response INVALID_RESPONSE = new Response(false, 1, "Missing/Invalid field(s)");
    private static final Response EXISTING_RESPONSE = new Response(false, 2, "The schedule exists");
    private static final Response NOT_FOUND_RESPONSE = new Response(false, 3, "Transport Schedule not found");


    @Autowired
    private TransportScheduleRepository scheduleRepository;

    @Autowired
    private TransportScheduleAsyncTasks asyncTasks;

    @GetMapping
    Page<TransportSchedule> getTransportSchedules(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                  @RequestParam(value = "sort", required = false) String sort,
                                                  @RequestParam(value = "direct", required = false) String direct){
        return scheduleRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.TRANSPORT_SCHEDULE_PARAMS));
    }

    @GetMapping("/{id}")
    TransportSchedule getTransportSchedule(@PathVariable String id){
        return scheduleRepository.findDetailOne(id);
    }


    @PostMapping
    Response createTransportSchedule(@RequestBody TransportSchedule schedule){
        if (schedule.getPrice() == null ||
                schedule.getPrice() < 0 ||
                schedule.getStartingTime() == null ||
                schedule.getEndingTime() == null)
            return INVALID_RESPONSE;
        TransportSchedule target = scheduleRepository.findOneExists(schedule.getPrice(), schedule.getStartingTime(), schedule.getEndingTime());
        if (target != null)
            return EXISTING_RESPONSE;

        asyncTasks.insertTransportSchedule(schedule);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @PostMapping("/{id}")
    Response updateTransportSchedule(@PathVariable String id, @RequestBody TransportSchedule schedule){
        TransportSchedule target = scheduleRepository.findTransportScheduleById(id);
        if (target == null)
            return NOT_FOUND_RESPONSE;
        if (schedule.getPrice() == null ||
                schedule.getPrice() < 0 ||
                schedule.getStartingTime() == null ||
                schedule.getEndingTime() == null)
            return INVALID_RESPONSE;

        asyncTasks.updateTransportSchedule(target, schedule);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping
    Response deleteTransportSchedule(@PathVariable String id){
         TransportSchedule target = scheduleRepository.findTransportScheduleById(id);
        if (target == null)
            return NOT_FOUND_RESPONSE;
        asyncTasks.deleteTransporter(id);
        return CommonResponse.SUCCESS_RESPONSE;
    }
}
