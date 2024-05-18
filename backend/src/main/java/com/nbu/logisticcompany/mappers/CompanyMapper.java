package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.dtos.company.CompanyCreateDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyUpdateDto;
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

    /**
     * Converts a CompanyCreateDto object to a Company object.
     *
     * @param companyCreateDTO CompanyCreateDto object to convert.
     * @return Converted Company object.
     * @throws IOException if an I/O error occurs.
     */
    public Company DtoToObject(CompanyCreateDto companyCreateDTO) throws IOException {
        Company company = new Company();
        ompany.setName(companyCreateDTO.getName());
        return company;
    }

    /**
     * Converts a Company object to a CompanyOutDto object.
     *
     * @param company Company object to convert.
     * @return Converted CompanyOutDto object.
     */
    public CompanyOutDto ObjectToDto(Company company) {
        CompanyOutDto companyOutDTO = new CompanyOutDto();
        companyOutDTO.setId(company.getId());
        companyOutDTO.setName(company.getName());
        return companyOutDTO;
    }

    /**
     * Converts a CompanyUpdateDto object to a Company object for update.
     *
     * @param companyUpdateDTO CompanyUpdateDto object containing updated company information.
     * @return Updated Company object.
     */
    public Company UpdateDtoToToCompany(CompanyUpdateDto companyUpdateDTO) {
        Company company = companyService.getById(companyUpdateDTO.getId());
        if (!companyUpdateDTO.getName().isEmpty()) {
            company.setName(companyUpdateDTO.getName());
        }
        return company;
    }

}
