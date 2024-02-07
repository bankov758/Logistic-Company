package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.*;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

//    public UserUpdateDTO objectToUpdateDto(User user) {
//        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
//        userUpdateDTO.setId(user.getId());
//        userUpdateDTO.setFirstName(user.getFirstName());
//        userUpdateDTO.setLastName(user.getLastName());
//        return userUpdateDTO;
//    }
//
//    public User UpdateDTOtoUser(UserUpdateDTO userDTO) {
//        User user = userService.getById(userDTO.getId());
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        if (!userDTO.getNewPassword().isEmpty()) {
//            user.setPassword(userDTO.getNewPassword());
//        }
//        return user;
//    }

}
