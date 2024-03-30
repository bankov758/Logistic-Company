package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.OfficeEmployee;

public interface OfficeEmployeeRepository extends BaseCRUDRepository<OfficeEmployee> {

    void removeUserFromOfficeEmployees(int officeEmployeeToDemoteId);

    void makeCourier(int officeEmployeeToUpdateId);

    boolean isAlreadyOfficeEmployee(int userId);

}
