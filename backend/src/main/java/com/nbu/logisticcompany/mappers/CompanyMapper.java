package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.*;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CompanyMapper {

    private final CompanyService companyService;
@Autowired
    public CompanyMapper(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Company DTOtoObject(CompanyRegisterDTO companyRegisterDTO) throws IOException {
        Company company = new Company();
        company.setName(companyRegisterDTO.getName());

        return company;
    }

    public CompanyOutDTO ObjectToDTO(Company company) {
        CompanyOutDTO companyOutDTO = new CompanyOutDTO();
        companyOutDTO.setId(company.getId());
        companyOutDTO.setName(company.getName());
        return companyOutDTO;
    }

    public CompanyUpdateDTO objectToUpdateDto(Company company) {
        CompanyUpdateDTO companyUpdateDTO = new CompanyUpdateDTO();

        companyUpdateDTO.setName(company.getName());
        return companyUpdateDTO;
    }
// Tova otdolu izglejda greshno
    public Company UpdateDTOtoToCompany(CompanyUpdateDTO companyUpdateDTO) {
        Company company = companyService.getByName(companyUpdateDTO.getName());

        if (!companyUpdateDTO.getName().isEmpty()) {
            company.setName(companyUpdateDTO.getName());
        }
        return company;
    }
}
