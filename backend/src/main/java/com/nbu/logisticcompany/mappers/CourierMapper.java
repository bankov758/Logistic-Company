package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.dtos.CourierOutDto;
import com.nbu.logisticcompany.entities.dtos.CourierRegisterDto;
import com.nbu.logisticcompany.entities.dtos.CourierUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourierMapper {

    private final CourierService courierService;
    private final CompanyService companyService;

    @Autowired
    public CourierMapper(CourierService courierService, CompanyService companyService) {
        this.courierService = courierService;
        this.companyService = companyService;
    }

    public Courier DTOtoObject(CourierRegisterDto courierRegisterDto) {
        Courier courier = new Courier();
        courier.setUsername(courierRegisterDto.getUsername());
        courier.setFirstName(courierRegisterDto.getFirstName());
        courier.setLastName(courierRegisterDto.getLastName());
        courier.setPassword(courierRegisterDto.getPassword());
        courier.setCompany(companyService.getByName(courierRegisterDto.getCompanyName()));
        return courier;
    }

    public CourierOutDto ObjectToDTO(Courier courier) {
        CourierOutDto courierOutDto = new CourierOutDto();
        courierOutDto.setId(courier.getId());
        courierOutDto.setUsername(courier.getUsername());
        courierOutDto.setFirstName(courier.getFirstName());
        courierOutDto.setLastName(courier.getLastName());
        courierOutDto.setCompanyName(courier.getCompany().getName());
        return courierOutDto;
    }

    public Courier UpdateDTOtoCourier(CourierUpdateDto courierDto) {
        Courier courier = courierService.getById(courierDto.getId());
        courier.setFirstName(courierDto.getFirstName());
        courier.setLastName(courierDto.getLastName());
        courier.setCompany(companyService.getByName(courierDto.getCompanyName()));
        if (StringUtil.isNotEmpty(courierDto.getNewPassword())) {
            courier.setPassword(courierDto.getNewPassword());
        }
        return courier;
    }

}
