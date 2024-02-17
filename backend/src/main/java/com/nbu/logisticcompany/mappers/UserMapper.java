package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.UserUpdateDto;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class UserMapper {

    public User DtoToObject(UserRegisterDto userRegisterDTO) throws IOException {
        User user = new User();
        setUserFieldsFromDto(user, userRegisterDTO);
        return user;
    }

    protected void setUserFieldsFromDto(User user, UserRegisterDto userRegisterDTO) {
        user.setUsername(userRegisterDTO.getUsername());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPassword(userRegisterDTO.getPassword());
        user.setRoles(Set.of(Role.USER));
    }

    public UserOutDto ObjectToDto(User user) {
        UserOutDto userOutDto = new UserOutDto();
        setFieldsFormObjectToOutDto(user, userOutDto);
        return userOutDto;
    }

    protected void setFieldsFormObjectToOutDto(User user, UserOutDto userOutDto) {
        userOutDto.setId(user.getId());
        userOutDto.setUsername(user.getUsername());
        userOutDto.setFirstName(user.getFirstName());
        userOutDto.setLastName(user.getLastName());
        userOutDto.setRoles(user.getRoles());
    }

    public User UpdateDtoToUser(UserUpdateDto userDto) {
        User user = new User();
        setFieldsFromUpdateDtoToObject(userDto, user);
        return user;
    }

    protected void setFieldsFromUpdateDtoToObject(UserUpdateDto userDto, User user) {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (ValidationUtil.isNotEmpty(userDto.getNewPassword())) {
            user.setPassword(userDto.getNewPassword());
        }
    }

}

