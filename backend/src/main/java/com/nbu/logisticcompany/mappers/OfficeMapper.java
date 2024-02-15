package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.dtos.*;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OfficeMapper {

    private final OfficeService officeService;
    @Autowired
    public OfficeMapper(OfficeService officeService) { this.officeService=officeService;}

    public Office DTOtoObject(OfficeCreateDto officeCreateDto) throws IOException {
        Office office = new Office();
        office.setAddress(officeCreateDto.getAddress());
        office.setCompany(officeCreateDto.getCompanyId());
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
