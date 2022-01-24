package com.lts.controller;

import com.lts.config.auth.ApplicationPrivilege;
import com.lts.model.entities.EmployeeReduction;
import com.lts.service.EmployeeReductionService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/employees-reduction")
public class EmployeeReductionController {

    private final EmployeeReductionService employeeReductionService;

    public EmployeeReductionController(EmployeeReductionService employeeReductionService) {
        this.employeeReductionService = employeeReductionService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestBody String fileContent, @RequestParam String fileName) {
        employeeReductionService.parseFile(fileName, fileContent);
    }

    @GetMapping
    public @ResponseBody
    Page<EmployeeReduction> getEmployeesReduction(@RequestParam int pageNumber,
                                                  @RequestParam int pageSize) {
        return employeeReductionService.getEmployeesReduction(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Page<EmployeeReduction> filterEmployeesReduction(@RequestParam String filter,
                                                     @RequestParam Object value,
                                                     @RequestParam int pageNumber,
                                                     @RequestParam int pageSize) {
        return employeeReductionService.filterEmployeesReduction(filter, value, pageNumber, pageSize);
    }

}