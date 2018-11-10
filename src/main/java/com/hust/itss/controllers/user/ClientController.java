package com.hust.itss.controllers.user;

import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.responses.Response;
import com.hust.itss.models.users.Client;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping
    protected Page<Client> getClients(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "page_size", required = false) Integer pageSize,
                                      @RequestParam(value = "sort", required = false) String sort,
                                      @RequestParam(value = "direct", required = false) String direct){
        System.out.println("GET: clients page " + page + ", sort by " + sort + ", direct " + direct);
        return clientRepository.findAllClients(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.USER_PARAMS));
    }

//    @PostMapping
//    Response createClient(@RequestBody(required = false) Client client){
//        if(client == null)
//            return new Response(false, 1, "empty requests");
//        if()
//    }

    @GetMapping("/{id}")
    protected Client getClient(@PathVariable String id){
        System.out.println("GET: one client " + id);
        return clientRepository.findClientById(id);
    }

    @PutMapping("/{id}")
    Response updateClient(@PathVariable String id,
                          @RequestBody(required = false) Client client){
        if(client == null)
            return new Response(false, 1, "empty requests body");

        Client updatedClient = clientRepository.findClientById(id);
        if(updatedClient == null){
            return new Response(false, 2, "wrong client id");
        }
        else if (client.getFullName() == null || client.getPhoneNumber() == null){
            return new Response(false, 3, "missing field(s)");
        }

        System.out.println("PUT: update client " + id);
        updatedClient.setFullName(client.getFullName());
        updatedClient.setFullName(client.getPhoneNumber());

        clientRepository.save(updatedClient);
        return new Response(true, 0, null);
    }

    @DeleteMapping("/{id}")
    Response deleteClient(@PathVariable String id){
        Client client = clientRepository.findClientById(id);
        if(client == null){
            return new Response(false, 1, "wrong ID");
        }

        System.out.println("DELETE: client " + id);
        clientRepository.delete(client);
        return new Response(true, 0, null);
    }

    @GetMapping("/filter")
    Page<Client> filterClients(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "page_size", required = false) Integer pageSize){
        System.out.println("GET: Filter client by name: " + name + ", phone: " + phone + ", page: " + page + ", page size: " + pageSize);
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        if(name == null) name = "";
        if(phone == null) phone = "";
        return clientRepository.filterClients(name, phone, PageRequest.of(page-1, pageSize));
    }
}
