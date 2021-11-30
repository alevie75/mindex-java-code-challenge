package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reporting/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Lennon");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Development Manager");

        int EXPECTED_NUM_DIRECT_REPORTS = 4;

        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setEmployee(testEmployee);

        // Create checks
        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testReportingStructure.getEmployee().getEmployeeId()).getBody();

        assertNotNull(createdReportingStructure);
        assertNotNull(createdReportingStructure.getEmployee());
        assertReportingStructureEquivalence(testEmployee, EXPECTED_NUM_DIRECT_REPORTS, createdReportingStructure);
    }

    private static void assertReportingStructureEquivalence(Employee expected, int expectedNumDirectReports, ReportingStructure actual){
        assertEmployeeEquivalence(expected, actual.getEmployee());
        assertEquals(expectedNumDirectReports, actual.getNumberOfReports());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
