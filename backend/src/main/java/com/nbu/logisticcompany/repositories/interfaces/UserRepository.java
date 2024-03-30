package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;

public interface UserRepository extends BaseCRUDRepository<User> {

    Company getEmployeeCompany(int employeeId);

    void makeOfficeEmployee(int userId, int officeId);

    void makeCourier(int userId, int companyId);

    void makeEmployee(int userId, int companyId);

}
