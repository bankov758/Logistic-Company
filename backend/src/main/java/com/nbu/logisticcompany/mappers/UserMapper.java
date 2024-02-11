package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.UserOutDto;
import com.nbu.logisticcompany.entities.dtos.UserRegisterDto;
import com.nbu.logisticcompany.entities.dtos.UserUpdateDto;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class UserMapper {

    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User DtoToObject(UserRegisterDto userRegisterDTO) throws IOException {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPassword(userRegisterDTO.getPassword());
        user.setRoles(Set.of(Role.USER));
        return user;
    }

    public UserOutDto ObjectToDto(User user) {
        UserOutDto userOutDto = new UserOutDto();
        userOutDto.setId(user.getId());
        userOutDto.setUsername(user.getUsername());
        userOutDto.setFirstName(user.getFirstName());
        userOutDto.setLastName(user.getLastName());
        userOutDto.setRoles(user.getRoles());
        return userOutDto;
    }

    public User UpdateDtoToUser(UserUpdateDto userDTO) {
        User user = userService.getById(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (!userDTO.getNewPassword().isEmpty()) {
            user.setPassword(userDTO.getNewPassword());
        }
        return user;
    }
}

