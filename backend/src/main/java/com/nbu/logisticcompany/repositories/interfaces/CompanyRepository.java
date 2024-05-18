package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyCouriersDto;
import com.nbu.logisticcompany.entities.dtos.user.OfficeEmployeeOutDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyRepository extends BaseCRUDRepository<Company> {

    List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd);
    List<OfficeEmployeeOutDto> getCompanyEmployees(int companyId, User user);
    List<ClientOutDto> getCompanyClients(int companyId, User user);
    List<CompanyCouriersDto> getCompanyCouriers(int companyId, User user);

}
