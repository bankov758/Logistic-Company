package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyRepository extends BaseCRUDRepository<Company> {

    List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd);
    List<CompanyEmployeesDto> getCompanyEmployees(int companyId, User user);
    List<ClientOutDto> getCompanyClients(int companyId, User user);

}
