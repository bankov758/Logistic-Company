package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.UserOutDTO;
import com.nbu.logisticcompany.entities.dto.UserRegisterDTO;
import com.nbu.logisticcompany.entities.dto.UserUpdateDTO;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.mappers.UserMapper;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;

    public UserController(UserService userService, AuthenticationHelper authenticationHelper, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserOutDTO> getAll(@RequestHeader HttpHeaders headers,
                                   @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(headers);
        return userService.getAll(search).stream()
                .map(userMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserOutDTO getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return userMapper.ObjectToDTO(userService.getById(id));
    }

    @PostMapping
    public User create(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User user = userMapper.DTOtoObject(userRegisterDTO);
            userService.create(user);
            return user;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping
    public User update(@RequestHeader HttpHeaders headers,
                       @Valid @RequestBody UserUpdateDTO userToUpdate) {
        User updater = authenticationHelper.tryGetUser(headers);
        User user = userMapper.UpdateDTOtoUser(userToUpdate);
        userService.update(user, updater);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        userService.delete(id, user);
    }

}
