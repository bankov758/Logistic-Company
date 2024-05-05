package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.UserUpdateDto;
import com.nbu.logisticcompany.services.interfaces.UserService;
import com.nbu.logisticcompany.utils.DataUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserMapper {

    private final UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    /**
     * Converts a UserRegisterDto object to a User object.
     *
     * @param userRegisterDTO UserRegisterDto object to convert.
     * @return Converted User object.
     * @throws IOException if an I/O error occurs.
     */
    public User dtoToObject(UserRegisterDto userRegisterDTO) throws IOException {
        User user = new User();
        setUserFieldsFromDto(user, userRegisterDTO);
        return user;
    }

    /**
     * Sets user fields from a UserRegisterDto object.
     *
     * @param user User object to update.
     * @param userRegisterDTO UserRegisterDto object containing new user information.
     */
    protected void setUserFieldsFromDto(User user, UserRegisterDto userRegisterDTO) {
        user.setUsername(userRegisterDTO.getUsername());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPassword(userRegisterDTO.getPassword());
    }

    /**
     * Converts a User object to a UserOutDto object.
     *
     * @param user User object to convert.
     * @return Converted UserOutDto object.
     */
    public UserOutDto ObjectToDto(User user) {
        UserOutDto userOutDto = new UserOutDto();
        setFieldsFormObjectToOutDto(user, userOutDto);
        return userOutDto;
    }

    /**
     * Sets fields from a User object to a UserOutDto object.
     *
     * @param user User object to retrieve information from.
     * @param userOutDto UserOutDto object to update.
     */
    protected void setFieldsFormObjectToOutDto(User user, UserOutDto userOutDto) {
        userOutDto.setId(user.getId());
        userOutDto.setUsername(user.getUsername());
        userOutDto.setFirstName(user.getFirstName());
        userOutDto.setLastName(user.getLastName());
        userOutDto.setRoles(user.getRoles());
    }

    /**
     * Converts a UserUpdateDto object to a User object for update.
     *
     * @param userDto UserUpdateDto object containing updated user information.
     * @return Updated User object.
     */
    public User UpdateDtoToUser(UserUpdateDto userDto) {
        User user = userService.getById(userDto.getId());
        setFieldsFromUpdateDtoToObject(userDto, user);
        return user;
    }

    /**
     * Sets fields from a UserUpdateDto object to a User object for update.
     *
     * @param userDto UserUpdateDto object containing updated user information.
     * @param user User object to update.
     */
    protected void setFieldsFromUpdateDtoToObject(UserUpdateDto userDto, User user) {
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (DataUtil.isNotEmpty(userDto.getNewPassword())) {
            user.setPassword(userDto.getNewPassword());
        }
    }

}

