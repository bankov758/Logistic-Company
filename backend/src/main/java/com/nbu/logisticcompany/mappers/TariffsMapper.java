package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffCreateDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffOutDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TariffsMapper {

    private final TariffsService tariffsService;
    private final CompanyService companyService;

    @Autowired
    public TariffsMapper(TariffsService tariffsService, CompanyService companyService){
        this.tariffsService=tariffsService;
        this.companyService = companyService;
    }

    /**
     * Converts a TariffCreateDto object to a Tariff object.
     *
     * @param tariffCreateDto TariffCreateDto object to convert.
     * @return Converted Tariff object.
     * @throws IOException if an I/O error occurs.
     */
    public Tariff DTOtoObject(TariffCreateDto tariffCreateDto) throws IOException{
        Tariff tariff = new Tariff();
        tariff.setCompany(companyService.getById(tariffCreateDto.getCompanyID()));
        tariff.setOfficeDiscount(tariffCreateDto.getOfficeDiscount());
        tariff.setPricePerKG(tariffCreateDto.getPricePerKG());
        return tariff;
    }

    /**
     * Converts a Tariff object to a TariffOutDto object.
     *
     * @param tariff Tariff object to convert.
     * @return Converted TariffOutDto object.
     */
    public TariffOutDto ObjectToDTO(Tariff tariff){
        TariffOutDto tariffOutDto = new TariffOutDto();
        tariffOutDto.setId(tariff.getId());
        tariffOutDto.setCompanyID(tariff.getCompany());
        tariffOutDto.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffOutDto.setPricePerKG(tariff.getPricePerKG());
        return tariffOutDto;
    }

    /**
     * Converts a Tariff object to a TariffUpdateDto object for update.
     *
     * @param tariff Tariff object to convert.
     * @return Converted TariffUpdateDto object.
     */
    private TariffUpdateDto objectToUpdateDTO(Tariff tariff){
        TariffUpdateDto tariffUpdateDto = new TariffUpdateDto();
        tariffUpdateDto.setCompanyId(tariff.getCompany());
        tariffUpdateDto.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffUpdateDto.setPricePerKg(tariff.getPricePerKG());
        return tariffUpdateDto;
    }

    /**
     * Converts a TariffUpdateDto object to a Tariff object for update.
     *
     * @param tariffUpdateDto TariffUpdateDto object containing updated tariff information.
     * @return Updated Tariff object.
     */
    public Tariff UpdateDTOtoTariffs(TariffUpdateDto tariffUpdateDto){
        Tariff tariff = new Tariff();
        tariff.setPricePerKG(tariffUpdateDto.getPricePerKg());
        tariff.setOfficeDiscount(tariffUpdateDto.getOfficeDiscount());
        tariff.setId(tariffUpdateDto.getId());
        tariff.setCompany(tariffUpdateDto.getCompanyId());

        return tariff;

    }

}
