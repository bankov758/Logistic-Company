package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository extends BaseCRUDRepository<Office> {

    List<Office> filter(Optional<String> address, Optional<Integer> companyId, Optional<String> sort);

    Company getOfficeCompany(int officeId);

}
