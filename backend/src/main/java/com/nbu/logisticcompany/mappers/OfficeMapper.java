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

    /**
     * Converts an OfficeCreateDto object to an Office object.
     *
     * @param officeCreateDto OfficeCreateDto object to convert.
     * @return Converted Office object.
     * @throws IOException if an I/O error occurs.
     */
    public Office DTOtoObject(OfficeCreateDto officeCreateDto) throws IOException {
        Office office = new Office();
        office.setAddress(officeCreateDto.getAddress());
        office.setCompany(companyService.getById(officeCreateDto.getCompanyId()));
        return office;
    }
    /**
     * Converts an Office object to an OfficeOutDto object.
     *
     * @param office Office object to convert.
     * @return Converted OfficeOutDto object.
     */
    public OfficeOutDto ObjectToDTO(Office office){
        OfficeOutDto officeOutDto = new OfficeOutDto();
        officeOutDto.setId(office.getId());
        officeOutDto.setAddress(office.getAddress());
        officeOutDto.setCompanyId(office.getCompany());
        return officeOutDto;
    }

    /**
     * Converts an Office object to an OfficeUpdateDto object for update.
     *
     * @param office Office object to convert.
     * @return Converted OfficeUpdateDto object.
     */
    private OfficeUpdateDto objectToUpdateDTO(Office office){
        OfficeUpdateDto officeUpdateDto = new OfficeUpdateDto();
        officeUpdateDto.setCompanyId(office.getCompany());
        officeUpdateDto.setAddress(office.getAddress());
        return officeUpdateDto;
    }

    /**
     * Converts an OfficeUpdateDto object to an Office object for update.
     *
     * @param officeUpdateDto OfficeUpdateDto object containing updated office information.
     * @return Updated Office object.
     */
    public Office UpdateDTOtoOffice(OfficeUpdateDto officeUpdateDto){
        Office office = new Office();
        office.setCompany(officeUpdateDto.getCompanyId());
        office.setAddress(officeUpdateDto.getAddress());
        office.setId(officeUpdateDto.getId());

        return office;

    }
}
