package com.hust.itss.controllers.user;

import com.hust.itss.constants.RequestParams;
import com.hust.itss.models.responses.Response;
import com.hust.itss.models.schedules.WorkSchedule;
import com.hust.itss.models.users.Employee;
import com.hust.itss.utils.PageRequestCreation;
import com.hust.itss.repositories.EmployeeRepository;
import com.hust.itss.repositories.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final WorkScheduleRepository workScheduleRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, WorkScheduleRepository workScheduleRepository) {
        this.employeeRepository = employeeRepository;
        this.workScheduleRepository = workScheduleRepository;
    }

    @GetMapping
    Page<Employee> getEmployees(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "page_size", required = false) Integer pageSize,
                                @RequestParam(value = "sort", required = false) String sort,
                                @RequestParam(value = "direct", required = false) String direct){

        System.out.println("GET: employees page " + page + ", page size " + pageSize + ", sort by " + sort + ", direct " + direct);
        return employeeRepository.findAll(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.USER_PARAMS));
    }

    @PostMapping
    Response createEmployee(@RequestBody(required = false) Employee employee){
        if (employee == null)
            return new Response(false, 1, "empty request");
        if(employeeRepository.findExistingEmployee(employee.getPhone(), employee.getEmail(), employee.getCitizenId()) != null){
            System.out.println("ERR: Existing employee !");
            return new Response(false, 2, "overlap field(s)");
        }
        System.out.println("POST: create employee " + employee.getName() + ", " + employee.getPhone() + ", " + employee.getEmail() + ", " + employee.getCitizenId());
        employeeRepository.save(employee);
        return new Response(true, 0, null);
    }

    @GetMapping("/{id}")
    Employee getEmployee(@PathVariable String id){
        System.out.println("GET: one employee " + id);
        return employeeRepository.findEmployeeById(id);
    }

    @PutMapping("/{id}")
    Response updateEmployee(@PathVariable String id,
                            @RequestBody(required = false) Employee employee){
        if(employee == null)
            return new Response(false, 1, "empty request");

        Employee updatedEmploye = employeeRepository.findEmployeeById(id);
        if(updatedEmploye == null)
            return new Response(false, 2, "wrong employee id");
        else if(employee.getName() == null || employee.getPhone() == null || employee.getAddress() == null || employee.getCitizenId() == null)
            return new Response(false, 3, "missing field(s)");

        System.out.println("PUT: update employee " + employee.getName());
        updatedEmploye.setName(employee.getName());
        updatedEmploye.setAddress(employee.getPhone());
        updatedEmploye.setEmail(employee.getEmail());
        updatedEmploye.setCitizenId(employee.getCitizenId());
        updatedEmploye.setAddress(employee.getAddress());

        employeeRepository.save(updatedEmploye);
        return new Response(true, 0, "");
    }

    @DeleteMapping("/{id}")
    Response deleteEmployee(@PathVariable String id){
        System.out.println("DELETE: delete employee " + id);
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null){
            return new Response(false, 1, "ID not found");
        }
        employeeRepository.delete(employee);
        return new Response(true, 0, null);
    }

    @GetMapping("/filter")
    Page<Employee> filterEmployees(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "phone", required = false) String phone,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "citizen_id", required = false) String citizenId,
                                   @RequestParam(value = "page_size", required = false) Integer pageSize){
        System.out.println("GET: Filter employees by name: " + name + ", phone: " + phone);
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        if(name == null) name = "";
        if(phone == null) phone = "";
        if(citizenId == null) citizenId = "";
        return employeeRepository.findEmployeesByNameAndPhoneNumber(name, phone, PageRequest.of(page - 1, pageSize));
    }

    @GetMapping("/{id}/work-schedule")
    WorkSchedule getWorkSchedule(@PathVariable String id){
        System.out.println("GET: Work Schedule of employee " + id);
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null) return null;
        return workScheduleRepository.findWorkScheduleById(employee.getWorkSchedule());
    }

}
