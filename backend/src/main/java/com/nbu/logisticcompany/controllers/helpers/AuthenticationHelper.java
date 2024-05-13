package com.nbu.logisticcompany.controllers.helpers;

import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.AuthenticationFailureException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class AuthenticationHelper {

    public static final String AUTHENTICATION_FAILURE_MESSAGE = "Wrong username or password.";
    public static final String LOGGED_USER_KEY = "currentUser";

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationHelper(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User tryGetUser(HttpSession session) {
        User currentUser = (User) session.getAttribute(LOGGED_USER_KEY);
        if (currentUser == null) {
            throw new AuthenticationFailureException("No user logged in.");
        }
        return userService.getByUsername(currentUser.getUsername());
    }

    public User verifyAuthentication(String username, String password) {
        try {
            User user = userService.getByUsername(username);
            String encryptedPassword = user.getPassword().length() < 60 ?
                    passwordEncoder.encode(user.getPassword()) : user.getPassword();
            if (!passwordEncoder.matches(password, encryptedPassword)) {
                throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
            }
            if (user.getPassword().length() < 60) {
                userService.update(user, user);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailureException(AUTHENTICATION_FAILURE_MESSAGE);
        }
    }

}

