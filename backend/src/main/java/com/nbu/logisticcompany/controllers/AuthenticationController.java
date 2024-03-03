package com.nbu.logisticcompany.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.UserLoginDto;
import com.nbu.logisticcompany.entities.dtos.user.UserRegisterDto;
import com.nbu.logisticcompany.exceptions.AuthenticationFailureException;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.mappers.UserMapper;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void handleLogin(@Valid UserLoginDto userLoginDto, BindingResult errors,
        HttpSession session) {
        try {
            if (errors.hasErrors()) {
                //return "login";
            }
            User loggedUser = authenticationHelper.verifyAuthentication(userLoginDto.getUsernamef(), userLoginDto.getPasswordf());
            session.setAttribute("currentUser", loggedUser);
        } catch (AuthenticationFailureException e) {
            errors.rejectValue("username", "auth.error", e.getMessage());
        }
    }

    @GetMapping("/logout")
    public void handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
    }

    @PostMapping("/signup")
    public void handleRegister(@Valid @ModelAttribute("register") UserRegisterDto register,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //return "signup";
        }

        if (!register.getPassword().equals(register.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password_error",
                                      "Password confirmation should match password.");
        }

        try {
            User user = userMapper.DtoToObject(register);
            userService.create(user);
        } catch (DuplicateEntityException | EntityNotFoundException d) {
            String[] exceptionMessage = d.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            bindingResult.rejectValue(fieldName, "user_error", d.getMessage());
        } catch (IOException i) {
            bindingResult.rejectValue("avatar", "avatar_error", i.getMessage());
        }
    }

}
