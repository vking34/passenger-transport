package com.hust.itss.controllers.schedule;

import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.schedules.TransportSchedule;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.TransportScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transport-schedule")
public class TransportScheduleController {
    @Autowired
    TransportScheduleRepository transportScheduleRepository;

    @GetMapping
    Page<TransportSchedule> getTransportSchedules(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "page_size", required = false) Integer pageSize,
                                                  @RequestParam(value = "sort", required = false) String sort,
                                                  @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: work schedules page " + page + ", page size: " + pageSize + ", sort by " + sort + ", direct " + direct);
        return transportScheduleRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.TRANSPORT_SCHEDULE_PARAMS));
    }
}
