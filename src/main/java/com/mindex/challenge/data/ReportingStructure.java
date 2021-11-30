package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure(){
        //empty constructor
    }

    public void setEmployee(Employee employee) {
        // set employee and update calculation of number of reports
        this.employee = employee;
        // subtracting 1 to get rid of employee 'self' in return
        this.numberOfReports = this.calculateReportsFromEmployee(this.employee) - 1;
    }

    public Employee getEmployee() {
        return employee;
    }

    private int calculateReportsFromEmployee(Employee employee){
        int numReports = 0;
        List<Employee> directReports =  employee.getDirectReports();
        if (directReports == null){
            return 1;
        }
        for (Employee reportee: directReports){
            // iterate through all reporting employees and get the count of all of their reporting employees
            numReports += this.calculateReportsFromEmployee(reportee);
        }
        return numReports + 1; // count employee in their direct report count
    }

    public int getNumberOfReports(){
        return this.numberOfReports;
    }
}
