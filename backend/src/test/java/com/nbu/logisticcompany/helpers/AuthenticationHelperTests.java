package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.AuthenticationFailureException;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper.LOGGED_USER_KEY;

@ExtendWith(MockitoExtension.class)
public class AuthenticationHelperTests {

    @Mock
    UserService userService;

    @InjectMocks
    AuthenticationHelper authenticationHelper;

    @Test
    void tryGetUserShouldThrowIfNoUserLoggedIn() {
        Assertions.assertThrows(AuthenticationFailureException.class,
                                () -> authenticationHelper.tryGetUser(new MockHttpSession()));
    }

    @Test
    void tryGetUserShouldCallUserServiceIfUserLoggedIn() {
        MockHttpSession session = new MockHttpSession();
        User user = new User();
        user.setUsername("test username");
        session.setAttribute(LOGGED_USER_KEY, user);

        authenticationHelper.tryGetUser(session);

        Mockito.verify(userService, Mockito.times(1))
            .getByUsername("test username");
    }

}
