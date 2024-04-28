package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.entities.*;

import java.util.Set;

public class UserMockData {

    public static User createMockEmployee() {
        Employee employee = new Employee();
        employee.setCompany(new Company(1, "Test Company"));
        employee.setRoles(Set.of(Role.EMPLOYEE));
        return employee;
    }

    public static OfficeEmployee createMockOfficeEmployee() {
        OfficeEmployee officeEmployee = new OfficeEmployee();
        officeEmployee.setCompany(new Company(1, "Test Company"));
        officeEmployee.setRoles(Set.of(Role.EMPLOYEE));
        return officeEmployee;
    }

    public static Courier createMockCourier() {
        Courier courier = new Courier();
        courier.setCompany(new Company(1, "Test Company"));
        courier.setRoles(Set.of(Role.EMPLOYEE));
        return courier;
    }

}
