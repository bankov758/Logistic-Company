package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Company getByName(String name) {
        return null;
    }

    @Override
    public List<Company> getAll(Optional<String> search) {
        return null;
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
            //throw new DuplicateEntityException("User", "username", user.getUsername());
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
