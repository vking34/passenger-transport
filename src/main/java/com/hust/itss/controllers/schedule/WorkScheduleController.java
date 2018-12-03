package com.hust.itss.controllers.schedule;

import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.models.schedule.WorkSchedule;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.schedule.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-schedule")
public class WorkScheduleController {

    @Autowired
    WorkScheduleRepository workScheduleRepository;

    @GetMapping
    Page<WorkSchedule> getWorkSchedules(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "page_size", required = false) Integer pageSize,
                                        @RequestParam(value = "sort", required = false) String sort,
                                        @RequestParam(value = "direct", required = false) String direct){
        return workScheduleRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.WORK_SCHEDULE_PARAMS));
    }

    @GetMapping("/{id}")
    WorkSchedule getWorkSchedule(@PathVariable String id){
         return workScheduleRepository.findDetailOne(id);
    }

}
