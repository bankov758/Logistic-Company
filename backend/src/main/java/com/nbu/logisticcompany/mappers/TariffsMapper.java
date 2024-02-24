package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffCreateDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffOutDto;
import com.nbu.logisticcompany.entities.dtos.tariff.TariffUpdateDto;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TariffsMapper {

    private final TariffsService tariffsService;

    @Autowired
    public TariffsMapper(TariffsService tariffsService){
        this.tariffsService=tariffsService;
    }
    public Tariff DTOtoObject(TariffCreateDto tariffCreateDto) throws IOException{
        Tariff tariff = new Tariff();
        tariff.setCompanyID(tariffCreateDto.getCompanyID());
        tariff.setOfficeDiscount(tariffCreateDto.getOfficeDiscount());
        tariff.setPricePerKG(tariffCreateDto.getPricePerKG());
        return tariff;
    }

    public TariffOutDto ObjectToDTO(Tariff tariff){
        TariffOutDto tariffOutDto = new TariffOutDto();
        tariffOutDto.setId(tariff.getId());
        tariffOutDto.setCompanyID(tariff.getCompanyID());
        tariffOutDto.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffOutDto.setPricePerKG(tariff.getPricePerKG());
        return tariffOutDto;
    }

    private TariffUpdateDto objectToUpdateDTO(Tariff tariff){
        TariffUpdateDto tariffUpdateDto = new TariffUpdateDto();
        tariffUpdateDto.setCompanyID(tariff.getCompanyID());
        tariffUpdateDto.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffUpdateDto.setPricePerKG(tariff.getPricePerKG());
        return tariffUpdateDto;
    }

    public Tariff UpdateDTOtoTariffs(TariffUpdateDto tariffUpdateDto){
        Tariff tariff = new Tariff();
        tariff.setPricePerKG(tariffUpdateDto.getPricePerKG());
        tariff.setOfficeDiscount(tariffUpdateDto.getOfficeDiscount());
        tariff.setId(tariffUpdateDto.getId());
        tariff.setCompanyID(tariffUpdateDto.getCompanyID());

        return tariff;

    }

}
