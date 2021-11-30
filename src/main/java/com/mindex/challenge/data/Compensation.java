package com.mindex.challenge.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Compensation {
    private Employee employee;
    private int salary;
    private Date effectiveDate;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) throws ParseException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.effectiveDate = dateFormatter.parse(effectiveDate);
    }
}
