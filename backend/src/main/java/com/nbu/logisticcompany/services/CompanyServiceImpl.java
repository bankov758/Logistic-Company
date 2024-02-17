package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.CompanyOutDto;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
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
        return companyRepository.getByField("name",name);
    }

    @Override
    public List<Company> getAll(Optional<String> search) {
        return companyRepository.getAll();
    }

    @Override
    public List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd){
        return companyRepository.getCompanyIncome(companyId, periodStart, periodEnd);
    }

    @Override
    public void create(Company company) {
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

    @Override
    public void update(Company companyToUpdate, User user) {
       /* if (companyToUpdate.getId() != user.getId()) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
        }*/
        companyRepository.update(companyToUpdate);
    }

    @Override
    public void delete(int companyId, User user) {
       /* if (user.getId() != companyId) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
        }*/
        companyRepository.delete(companyId);
    }

}
