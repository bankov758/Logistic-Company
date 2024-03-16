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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/auth")
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
            session.setAttribute("currentUser", loggedUser);
            return ResponseEntity.ok().body(userMapper.ObjectToDto(loggedUser));
        } catch (AuthenticationFailureException e) {
            errors.rejectValue("username", "auth.error", e.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session.getAttribute("currentUser") == null){
            return ResponseEntity.notFound().build();
        }
        session.removeAttribute("currentUser");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto register, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }

        if (!register.getPassword().equals(register.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password_error",
                                      "Password confirmation should match password.");
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }

        try {
            User user = userMapper.DtoToObject(register);
            return ResponseEntity.ok().body(userMapper.ObjectToDto(userService.create(user)));
        } catch (DuplicateEntityException | EntityNotFoundException | IOException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return ResponseEntity.badRequest().body(getDefaultMessages(errors));
        }
    }

    private Stream<String> getDefaultMessages(BindingResult errors) {
        return errors.getFieldErrors().stream().map(FieldError::getDefaultMessage);
    }

}
