package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Tariffs;
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
    public Tariffs DTOtoObject(TariffsCreateDTO tariffsCreateDTO) throws IOException{
        Tariffs tariffs = new Tariffs();
        tariffs.setCompanyID(tariffsCreateDTO.getCompanyID());
        tariffs.setOfficeDiscount(tariffsCreateDTO.getOfficeDiscount());
        tariffs.setPricePerKG(tariffsCreateDTO.getPricePerKG());
        return tariffs;
    }

    public TariffsOutDTO ObjectToDTO(Tariffs tariffs){
        TariffsOutDTO tariffsOutDTO = new TariffsOutDTO();
        tariffsOutDTO.setId(tariffs.getId());
        tariffsOutDTO.setCompanyID(tariffs.getCompanyID());
        tariffsOutDTO.setOfficeDiscount(tariffs.getOfficeDiscount());
        tariffsOutDTO.setPricePerKG(tariffs.getPricePerKG());
        return tariffsOutDTO;
    }

    private TariffsUpdateDTO objectToUpdateDTO(Tariffs tariffs){
        TariffsUpdateDTO tariffsUpdateDTO = new TariffsUpdateDTO();
        tariffsUpdateDTO.setCompanyID(tariffs.getCompanyID());
        tariffsUpdateDTO.setOfficeDiscount(tariffs.getOfficeDiscount());
        tariffsUpdateDTO.setPricePerKG(tariffs.getPricePerKG());
        return tariffsUpdateDTO;
    }

    public Tariffs UpdateDTOtoTariffs(TariffsUpdateDTO tariffsUpdateDTO){
        Tariffs tariffs = new Tariffs();
        tariffs.setPricePerKG(tariffsUpdateDTO.getPricePerKG());
        tariffs.setOfficeDiscount(tariffsUpdateDTO.getOfficeDiscount());
        tariffs.setId(tariffsUpdateDTO.getId());
        tariffs.setCompanyID(tariffsUpdateDTO.getCompanyID());

        return tariffs;

    }

}
