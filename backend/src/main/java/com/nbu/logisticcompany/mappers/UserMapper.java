package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.UserOutDTO;
import com.nbu.logisticcompany.entities.dto.UserRegisterDTO;
import com.nbu.logisticcompany.entities.dto.UserUpdateDTO;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserMapper {
    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User DTOtoObject(UserRegisterDTO userRegisterDTO) throws IOException {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPassword(userRegisterDTO.getPassword());
        return user;
    }

    public UserOutDTO ObjectToDTO(User user) {
        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setId(user.getId());
        userOutDTO.setUsername(user.getUsername());
        userOutDTO.setFirstName(user.getFirstName());
        userOutDTO.setLastName(user.getLastName());
        return userOutDTO;
    }

    public UserUpdateDTO objectToUpdateDto(User user) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setId(user.getId());
        userUpdateDTO.setFirstName(user.getFirstName());
        userUpdateDTO.setLastName(user.getLastName());
        return userUpdateDTO;
    }

    public User UpdateDTOtoUser(UserUpdateDTO userDTO) {
        User user = userService.getById(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (!userDTO.getNewPassword().isEmpty()) {
            user.setPassword(userDTO.getNewPassword());
        }
        return user;
    }
}

