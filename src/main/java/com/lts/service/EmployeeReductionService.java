package com.lts.service;

import com.lts.model.entities.EmployeeReduction;
import org.springframework.data.domain.Page;

public interface EmployeeReductionService {

    void parseFile(String filename, String fileContent);

    Page<EmployeeReduction> getEmployeesReduction(int pageNumber, int pageSize);

    Page<EmployeeReduction> filterEmployeesReduction(String filter, Object value, int pageNumber, int pageSize);

    EmployeeReduction getEmployeeReduction(long id);

}
