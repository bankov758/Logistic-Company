package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company getById(int id);

    Company getByName(String name);

    List<Company> getAll(Optional<String> search);

    List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd);

    List<CompanyEmployeesDto> getCompanyEmployees(int companyId, User user);

    List<ClientOutDto> getCompanyClients(int companyId, User user);

    void create(Company company, User creator);

    void update(Company companyToUpdate, User user );

    void delete(int companyId, User user);

}
