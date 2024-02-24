package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.dtos.user.CourierOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourierMapper extends UserMapper {

    private final CompanyService companyService;

    @Autowired
    public CourierMapper(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Courier DtoToObject(CourierRegisterDto courierRegisterDto) {
        Courier courier = new Courier();
        setUserFieldsFromDto(courier, courierRegisterDto);
        courier.setCompany(companyService.getByName(courierRegisterDto.getCompanyName()));
        return courier;
    }

    public CourierOutDto ObjectToDto(Courier courier) {
        CourierOutDto courierOutDto = new CourierOutDto();
        setFieldsFormObjectToOutDto(courier, courierOutDto);
        courierOutDto.setCompanyName(courier.getCompany().getName());
        return courierOutDto;
    }

    public Courier UpdateDtoToCourier(CourierUpdateDto courierDto) {
        Courier courier = new Courier();
        setFieldsFromUpdateDtoToObject(courierDto, courier);
        return courier;
    }

}
