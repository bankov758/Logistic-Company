package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.dtos.*;
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

    public Company DTOtoObject(CompanyCreateDto companyCreateDTO) throws IOException {
        Company company = new Company();
        company.setName(companyCreateDTO.getName());

        return company;
    }

    public CompanyOutDto ObjectToDTO(Company company) {
        CompanyOutDto companyOutDTO = new CompanyOutDto();
        companyOutDTO.setId(company.getId());
        companyOutDTO.setName(company.getName());
        return companyOutDTO;
    }

    public CompanyUpdateDto objectToUpdateDto(Company company) {
        CompanyUpdateDto companyUpdateDTO = new CompanyUpdateDto();

        companyUpdateDTO.setName(company.getName());
        return companyUpdateDTO;
    }
// Tova otdolu izglejda greshno
    public Company UpdateDTOtoToCompany(CompanyUpdateDto companyUpdateDTO) {
        Company company = companyService.getByName(companyUpdateDTO.getName());

        if (!companyUpdateDTO.getName().isEmpty()) {
            company.setName(companyUpdateDTO.getName());
        }
        return company;
    }
}
