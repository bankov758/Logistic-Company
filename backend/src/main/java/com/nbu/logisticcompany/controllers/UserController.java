package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRole;
import com.nbu.logisticcompany.entities.dtos.user.UserUpdateDto;
import com.nbu.logisticcompany.mappers.UserMapper;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
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
    public List<UserOutDto> getAll(HttpSession session,
                                   @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(session);
        return userService.getAll(search).stream()
                .map(userMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserOutDto getById(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return userMapper.ObjectToDto(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRegisterDto userRegisterDTO) {
        try {
            User user = userMapper.DtoToObject(userRegisterDTO);
            userService.create(user);
            return ResponseEntity.ok().body(userRegisterDTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(HttpSession session,
                                    @Valid @RequestBody UserUpdateDto userToUpdate) {
        User updater = authenticationHelper.tryGetUser(session);
        User user = userMapper.UpdateDtoToUser(userToUpdate);
        userService.update(user, updater);
        return ResponseEntity.ok().body(userToUpdate);
    }

    @PutMapping("/add-role")
    public User addRole(HttpSession session,
                        @Valid @RequestBody UserRole userRole) {
        User updater = authenticationHelper.tryGetUser(session);
        User user = userService.getById(userRole.getUserId());
        userService.addRole(user, userRole.getRole(), updater);
        return user;
    }

    @PutMapping("/remove-role")
    public User removeRole(HttpSession session,
                           @Valid @RequestBody UserRole userRole) {
        User updater = authenticationHelper.tryGetUser(session);
        User user = userService.getById(userRole.getUserId());
        userService.removeRole(user, userRole.getRole(), updater);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(HttpSession session, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(session);
        userService.delete(id, user);
    }

}
