package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.UserLoginDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRegisterDto;
import com.nbu.logisticcompany.exceptions.AuthenticationFailureException;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.mappers.UserMapper;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper.LOGGED_USER_KEY;
import static com.nbu.logisticcompany.utils.DataUtil.getDefaultMessages;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;

    public AuthenticationController(UserService userService, AuthenticationHelper authenticationHelper,
        UserMapper userMapper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto,
                                   BindingResult errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            User loggedUser = authenticationHelper.verifyAuthentication(userLoginDto.getUsername(), userLoginDto.getPassword());
            session.setAttribute(LOGGED_USER_KEY, loggedUser);
            return ResponseEntity.ok().body(userMapper.ObjectToDto(loggedUser));
        } catch (AuthenticationFailureException e) {
            errors.rejectValue("username", "auth.error", e.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session.getAttribute(LOGGED_USER_KEY) == null){
            return ResponseEntity.notFound().build();
        }
        session.removeAttribute(LOGGED_USER_KEY);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto register,
                                      BindingResult errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            if (!register.getPassword().equals(register.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password_error",
                        "Password confirmation should match password.");
                return ResponseEntity.badRequest().body(getDefaultMessages(errors));
            }
            User user = userMapper.dtoToObject(register);
            User newUser = userService.create(user);
            session.setAttribute(LOGGED_USER_KEY, newUser);
            return ResponseEntity.ok().body(userMapper.ObjectToDto(newUser));
        } catch (DuplicateEntityException | EntityNotFoundException | IOException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }



}
