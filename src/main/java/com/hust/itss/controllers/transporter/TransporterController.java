package com.hust.itss.controllers.transporter;

import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.transporters.Transporter;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transporter")
public class TransporterController {

    private final TransporterRepository transporterRepository;

    @Autowired
    public TransporterController(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    @GetMapping
    Page<Transporter> getTransporters(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "page_size", required = false) Integer pageSize,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: transporter page " + page + ", page size " + pageSize + ", sort by " + sort + ", direct " + direct);
        return transporterRepository.findAll(PageRequestCreation.getPageRequest(page,pageSize, sort, direct, RequestParams.TRANSPORTER_PARAMS));
    }


}
