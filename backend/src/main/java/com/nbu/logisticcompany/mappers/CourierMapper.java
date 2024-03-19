package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.dtos.user.CourierOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.CourierUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourierMapper extends UserMapper {

    private final CompanyService companyService;
    private final CourierService courierService;

    @Autowired
    public CourierMapper(CompanyService companyService, CourierService courierService, UserService userService) {
        super(userService);
        this.companyService = companyService;
        this.courierService = courierService;
    }

    /**
     * Converts a CourierRegisterDto object to a Courier object.
     *
     * @param courierRegisterDto CourierRegisterDto object to convert.
     * @return Converted Courier object.
     */
    public Courier DtoToObject(CourierRegisterDto courierRegisterDto) {
        Courier courier = new Courier();
        setUserFieldsFromDto(courier, courierRegisterDto);
        courier.setCompany(companyService.getByName(courierRegisterDto.getCompanyName()));
        return courier;
    }

    /**
     * Converts a Courier object to a CourierOutDto object.
     *
     * @param courier Courier object to convert.
     * @return Converted CourierOutDto object.
     */
    public CourierOutDto ObjectToDto(Courier courier) {
        CourierOutDto courierOutDto = new CourierOutDto();
        setFieldsFormObjectToOutDto(courier, courierOutDto);
        courierOutDto.setCompanyName(courier.getCompany().getName());
        return courierOutDto;
    }

    /**
     * Converts a CourierUpdateDto object to a Courier object for update.
     *
     * @param courierDto CourierUpdateDto object containing updated courier information.
     * @return Updated Courier object.
     */
    public Courier UpdateDtoToCourier(CourierUpdateDto courierDto) {
        Courier courier = courierService.getById(courierDto.getId());
        setFieldsFromUpdateDtoToObject(courierDto, courier);
        courier.setCompany(companyService.getByName(courierDto.getCompanyName()));
        return courier;
    }

}
