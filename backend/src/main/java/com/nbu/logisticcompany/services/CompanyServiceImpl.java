package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyCouriersDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.utils.Action;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getById(int id) {
        return companyRepository.getById(id);
    }

    @Override
    public Company getByName(String name) {
        return companyRepository.getByField("name", name);
    }

    @Override
    public List<Company> getAll(Optional<String> search) {
        return companyRepository.getAll();
    }

    @Override
    public List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        return companyRepository.getCompanyIncome(companyId, periodStart, periodEnd);
    }

    @Override
    public List<CompanyEmployeesDto> getCompanyEmployees(int companyId, User user) {
        return companyRepository.getCompanyEmployees(companyId, user);
    }

    @Override
    public List<ClientOutDto> getCompanyClients(int companyId, User user) {
        return companyRepository.getCompanyClients(companyId, user);
    }

    @Override
    public List<CompanyCouriersDto> getCompanyCouriers(int companyId, User user) {
        return companyRepository.getCompanyCouriers(companyId, user);
    }

    /**
     * Creates a new company after ensuring no duplicate exists and the creator has admin privileges.
     *
     * @param company The company to be created.
     * @param creator The user attempting to create the company.
     * @throws UnauthorizedOperationException If the creator is not an admin.
     * @throws DuplicateEntityException       If a company with the same name already exists.
     */
    @Override
    public void create(Company company, User creator) {
        ValidationUtil.validateAdminAction(creator, Company.class, Action.CREATE);
        boolean duplicateCompany = true;
        try {
            companyRepository.getByField("name", company.getName());
        } catch (EntityNotFoundException e) {
            duplicateCompany = false;
        }
        if (duplicateCompany) {
            throw new DuplicateEntityException(Company.class.getSimpleName(), "name", company.getName());
        }
        companyRepository.create(company);
    }

    /**
     * Updates a company's information, ensuring the operation is performed by an authorized admin.
     *
     * @param companyToUpdate The company entity with updated information.
     * @param user            The admin user performing the update.
     * @throws UnauthorizedOperationException If the user is not authorized as an admin.
     * @throws DuplicateEntityException       If a company with the same name already exists.
     */
    @Override
    public void update(Company companyToUpdate, User user) {
        ValidationUtil.validateAdminAction(user, Company.class, Action.UPDATE);
        boolean duplicateCompany = true;
        try {
            companyRepository.getByField("name", companyToUpdate.getName());
        } catch (EntityNotFoundException e) {
            duplicateCompany = false;
        }
        if (duplicateCompany) {
            throw new DuplicateEntityException(Company.class.getSimpleName(), "name", companyToUpdate.getName());
        }
        companyRepository.update(companyToUpdate);
    }

    /**
     * Deletes a company by its ID, ensuring the operation is performed by an authorized admin.
     *
     * @param companyId The ID of the company to be deleted.
     * @param user      The admin user attempting the deletion.
     * @throws UnauthorizedOperationException If the user is not authorized as an admin.
     */
    @Override
    public void delete(int companyId, User user) {
        ValidationUtil.validateAdminAction(user, Company.class, Action.DELETE);
        companyRepository.delete(companyId);
    }

}
