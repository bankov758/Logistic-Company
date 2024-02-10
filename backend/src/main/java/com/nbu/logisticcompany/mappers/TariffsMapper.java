package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.dto.TariffsCreateDTO;
import com.nbu.logisticcompany.entities.dto.TariffsOutDTO;
import com.nbu.logisticcompany.entities.dto.TariffsUpdateDTO;
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
    public Tariff DTOtoObject(TariffsCreateDTO tariffsCreateDTO) throws IOException{
        Tariff tariff = new Tariff();
        tariff.setCompanyID(tariffsCreateDTO.getCompanyID());
        tariff.setOfficeDiscount(tariffsCreateDTO.getOfficeDiscount());
        tariff.setPricePerKG(tariffsCreateDTO.getPricePerKG());
        return tariff;
    }

    public TariffsOutDTO ObjectToDTO(Tariff tariff){
        TariffsOutDTO tariffsOutDTO = new TariffsOutDTO();
        tariffsOutDTO.setId(tariff.getId());
        tariffsOutDTO.setCompanyID(tariff.getCompanyID());
        tariffsOutDTO.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffsOutDTO.setPricePerKG(tariff.getPricePerKG());
        return tariffsOutDTO;
    }

    private TariffsUpdateDTO objectToUpdateDTO(Tariff tariff){
        TariffsUpdateDTO tariffsUpdateDTO = new TariffsUpdateDTO();
        tariffsUpdateDTO.setCompanyID(tariff.getCompanyID());
        tariffsUpdateDTO.setOfficeDiscount(tariff.getOfficeDiscount());
        tariffsUpdateDTO.setPricePerKG(tariff.getPricePerKG());
        return tariffsUpdateDTO;
    }

    public Tariff UpdateDTOtoTariffs(TariffsUpdateDTO tariffsUpdateDTO){
        Tariff tariff = new Tariff();
        tariff.setPricePerKG(tariffsUpdateDTO.getPricePerKG());
        tariff.setOfficeDiscount(tariffsUpdateDTO.getOfficeDiscount());
        tariff.setId(tariffsUpdateDTO.getId());
        tariff.setCompanyID(tariffsUpdateDTO.getCompanyID());

        return tariff;

    }

}
