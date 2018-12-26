package com.hust.itss.controllers.user;

import com.hust.itss.constants.request.RequestParams;
import com.hust.itss.constants.response.CommonResponse;
import com.hust.itss.constants.response.ErrorResponse;
import com.hust.itss.models.response.Response;
import com.hust.itss.models.schedule.WorkSchedule;
import com.hust.itss.models.user.Driver;
import com.hust.itss.models.user.Employee;
import com.hust.itss.utils.request.PageRequestCreation;
import com.hust.itss.repositories.user.EmployeeRepository;
import com.hust.itss.repositories.schedule.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.hust.itss.constants.request.RequestParams.*;

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
    Page<Employee> getEmployees(@RequestParam(value = PAGE, required = false) Integer page,
                                @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize,
                                @RequestParam(value = SORT, required = false) String sort,
                                @RequestParam(value = DIRECT, required = false) String direct){
        return employeeRepository.findAllEmployees(PageRequestCreation.getPageRequest(page, pageSize, sort, direct, RequestParams.USER_PARAMS));
    }

    @PostMapping
    Response createEmployee(@RequestBody(required = false) Employee employee){
        if (employee == null)
            return ErrorResponse.EMPTY_BODY;
        if(employeeRepository.findExistingEmployee(employee.getPhoneNumber(), employee.getEmail(), employee.getCitizenId()) != null){
            return ErrorResponse.EXISTING_ENTITY;
        }

        employeeRepository.save(employee);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @GetMapping("/driver")
    Page<Driver> getDrivers(@RequestParam(value = PAGE, required = false) Integer page,
                            @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize,
                            @RequestParam(value = SORT, required = false) String sort,
                            @RequestParam(value = DIRECT, required = false) String direct){
        return employeeRepository.findAllDrivers(PageRequestCreation.getBasicPageRequest(page, pageSize, sort, direct));
    }

    @GetMapping("/assistant")
    Page<Employee> getAssistants(@RequestParam(value = PAGE, required = false) Integer page,
                              @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize,
                              @RequestParam(value = SORT, required = false) String sort,
                              @RequestParam(value = DIRECT, required = false) String direct){
        return employeeRepository.findAllAssistants(PageRequestCreation.getBasicPageRequest(page, pageSize, sort, direct));
    }

    @GetMapping("/{id}")
    Employee getEmployee(@PathVariable String id){
        return employeeRepository.findEmployeeById(id);
    }

    @PutMapping("/{id}")
    Response updateEmployee(@PathVariable String id,
                            @RequestBody(required = false) Employee employee){
        if(employee == null)
            return ErrorResponse.EMPTY_BODY;

        Employee updatedEmploye = employeeRepository.findEmployeeById(id);
        if(updatedEmploye == null)
            return ErrorResponse.WRONG_ID;
        else if(employee.getFullName() == null || employee.getPhoneNumber() == null || employee.getAddress() == null || employee.getCitizenId() == null)
            return ErrorResponse.MISSING_FIELDS;

        updatedEmploye.setFullName(employee.getFullName());
        updatedEmploye.setAddress(employee.getPhoneNumber());
        updatedEmploye.setEmail(employee.getEmail());
        updatedEmploye.setCitizenId(employee.getCitizenId());
        updatedEmploye.setAddress(employee.getAddress());

        employeeRepository.save(updatedEmploye);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @DeleteMapping("/{id}")
    Response deleteEmployee(@PathVariable String id){
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null){
            return ErrorResponse.WRONG_ID;
        }
        employeeRepository.delete(employee);
        return CommonResponse.SUCCESS_RESPONSE;
    }

    @GetMapping("/filter")
    Page<Employee> filterEmployees(@RequestParam(value = NAME, required = false) String name,
                                   @RequestParam(value = PHONE, required = false) String phone,
                                   @RequestParam(value = CITIZEN_ID, required = false) String citizenId,
                                   @RequestParam(value = PAGE, required = false) Integer page,
                                   @RequestParam(value = PAGE_SIZE, required = false) Integer pageSize){
        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        if(name == null) name = "";
        if(phone == null) phone = "";
        if(citizenId == null) citizenId = "";
        return employeeRepository.findEmployeesByNameAndPhoneNumber(name, phone, PageRequest.of(page - 1, pageSize));
    }

    @GetMapping("/{id}/work-schedule")
    WorkSchedule getWorkSchedule(@PathVariable String id){
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null) return null;
        return workScheduleRepository.findWorkScheduleById(employee.getWorkSchedule());
    }

}
