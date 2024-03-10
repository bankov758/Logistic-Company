package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.OfficeEmployee;

public interface OfficeEmployeeRepository extends BaseCRUDRepository<OfficeEmployee> {

    Company getCompany(int officeEmployeeId);

}
