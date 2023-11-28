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
        try {
            authenticationHelper.tryGetUser(headers);
            return userService.getAll(search).stream()
                    .map(userMapper::ObjectToDTO)
                    .collect(Collectors.toList());
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        }
    }

    @GetMapping("/{id}")
    public UserOutDTO getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            authenticationHelper.tryGetUser(headers);
            return userMapper.ObjectToDTO(userService.getById(id));
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User user = userMapper.DTOtoObject(userRegisterDTO);
            userService.create(user);
            return user;
        } catch (DuplicateEntityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public User update(@RequestHeader HttpHeaders headers,
                       @Valid @RequestBody UserUpdateDTO userToUpdate) {
        try {
            User updater = authenticationHelper.tryGetUser(headers);
            User user = userMapper.UpdateDTOtoUser(userToUpdate);
            userService.update(user, updater);
            return user;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            userService.delete(id, user);
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
