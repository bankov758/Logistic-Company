package com.nbu.logisticcompany.mappers;

import java.io.IOException;

import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.dtos.office.OfficeCreateDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeOutDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {

    private final CompanyService companyService;

    @Autowired
    public OfficeMapper(CompanyService companyService) {
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
    public OfficeOutDto ObjectToDTO(Office office) {
        OfficeOutDto officeOutDto = new OfficeOutDto();
        officeOutDto.setId(office.getId());
        officeOutDto.setAddress(office.getAddress());
        officeOutDto.setCompanyId(office.getCompany());
        return officeOutDto;
    }

    /**
     * Converts an OfficeUpdateDto object to an Office object for update.
     *
     * @param officeUpdateDto OfficeUpdateDto object containing updated office information.
     * @return Updated Office object.
     */
    public Office UpdateDTOtoOffice(OfficeUpdateDto officeUpdateDto) {
        Office office = new Office();
        office.setAddress(officeUpdateDto.getAddress());
        office.setId(officeUpdateDto.getId());
        return office;
    }

}
