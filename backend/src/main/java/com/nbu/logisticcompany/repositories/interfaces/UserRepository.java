package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseCRUDRepository<User> {

    List<User> search(Optional<String> search);

    Company getEmployeeCompany(int employeeId);

    void makeOfficeEmployee(int userId, int officeId);

    void makeCourier(int userId, int companyId);

    void makeEmployee(int userId, int companyId);

    boolean isAlreadyEmployee(int userId);

}
