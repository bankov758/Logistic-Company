package com.nbu.logisticcompany.mock;

import com.nbu.logisticcompany.entities.*;

import java.util.Set;

public class UserMockData {

    public static User createMockAdmin() {
        return createMockUser(Role.ADMIN);
    }

    public static User createMockUser(Role role) {
        User user = new Employee();
        user.setRoles(Set.of(role));
        return user;
    }

    public static User createMockEmployee() {
        Employee employee = new Employee();
        employee.setCompany(new Company(1, "Test Company"));
        employee.setRoles(Set.of(Role.EMPLOYEE));
        return employee;
    }

    public static OfficeEmployee createMockOfficeEmployee() {
        return createMockOfficeEmployee(new Company(1, "Test Company"));
    }

    public static OfficeEmployee createMockOfficeEmployee(Company company) {
        OfficeEmployee officeEmployee = new OfficeEmployee();
        officeEmployee.setCompany(company);
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
