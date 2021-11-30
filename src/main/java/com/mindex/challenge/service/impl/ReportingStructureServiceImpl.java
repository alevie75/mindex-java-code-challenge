package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure create(String id) {
        LOG.debug("Creating reporting structure for employee with id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();
        Employee employee = this.createEmployeeReports(id);
        reportingStructure.setEmployee(employee);

        return reportingStructure;
    }

    private Employee createEmployeeReports(String id){
        LOG.debug("Building direct report list for employee with id [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        List<Employee> directReports = employee.getDirectReports();
        if (directReports == null){
            return employee;
        }
        for (int idx = 0; idx < directReports.size(); idx++){
            // get employee id from list and build out employee direct reports
            Employee e = this.createEmployeeReports(directReports.get(idx).getEmployeeId());
            // update employee direct report list
            directReports.set(idx, e);
        }

        //return updated employee
        return employee;
    }
}

