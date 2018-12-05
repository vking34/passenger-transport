package com.hust.itss.controllers.user;

import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.constants.response.ErrorResponse;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.user.Client;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class
ClientController {

    @Autowired
    ClientRepository clientRepository;



    @GetMapping
    protected Page<Client> getClients(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "page_size", required = false) Integer pageSize,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "direct", required = false) String direct){
        return clientRepository.findAllClients(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.USER_PARAMS));
    }

    @GetMapping("/{id}")
    protected Client getClient(@PathVariable String id){
        return clientRepository.findClientById(id);
    }

    @PutMapping("/{id}")
    Response updateClient(@PathVariable String id,
                          @RequestBody(required = false) Client client){
        if(client == null)
            return ErrorResponse.EMPTY_BODY;

        Client updatedClient = clientRepository.findClientById(id);
        if(updatedClient == null){
            return ErrorResponse.WRONG_ID;
        }
        else if (client.getFullName() == null || client.getPhoneNumber() == null){
            return ErrorResponse.MISSING_FIELDS;
        }

        updatedClient.setFullName(client.getFullName());
        updatedClient.setFullName(client.getPhoneNumber());

        clientRepository.save(updatedClient);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteClient(@PathVariable String id){
        Client client = clientRepository.findClientById(id);
        if(client == null){
            return ErrorResponse.WRONG_ID;
        }

        clientRepository.delete(client);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @GetMapping("/filter")
    Page<Client> filterClients(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "page_size", required = false) Integer pageSize){
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        if(name == null) name = "";
        if(phone == null) phone = "";
        return clientRepository.filterClients(name, phone, PageRequest.of(page-1, pageSize));
    }
}
