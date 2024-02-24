package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.dtos.office.OfficeCreateDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeOutDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OfficeMapper {

    private final OfficeService officeService;
    private final CompanyService companyService;

    @Autowired
    public OfficeMapper(OfficeService officeService, CompanyService companyService) { this.officeService=officeService;
        this.companyService = companyService;
    }

    public Office DTOtoObject(OfficeCreateDto officeCreateDto) throws IOException {
        Office office = new Office();
        office.setAddress(officeCreateDto.getAddress());
        office.setCompany(companyService.getById(officeCreateDto.getCompanyId()));
        return office;
    }
    public OfficeOutDto ObjectToDTO(Office office){
        OfficeOutDto officeOutDto = new OfficeOutDto();
        officeOutDto.setId(office.getId());
        officeOutDto.setAddress(office.getAddress());
        officeOutDto.setCompanyId(office.getCompany());
        return officeOutDto;
    }

    private OfficeUpdateDto objectToUpdateDTO(Office office){
        OfficeUpdateDto officeUpdateDto = new OfficeUpdateDto();
        officeUpdateDto.setCompanyId(office.getCompany());
        officeUpdateDto.setAddress(office.getAddress());
        return officeUpdateDto;
    }

    public Office UpdateDTOtoOffice(OfficeUpdateDto officeUpdateDto){
        Office office = new Office();
        office.setCompany(officeUpdateDto.getCompanyId());
        office.setAddress(officeUpdateDto.getAddress());
        office.setId(officeUpdateDto.getId());

        return office;

    }
}
