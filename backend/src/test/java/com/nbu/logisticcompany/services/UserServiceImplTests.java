package com.nbu.logisticcompany.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.helpers.UserMockData;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.utils.Action;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    UserRepository userRepository;
    @Mock
    OfficeEmployeeRepository officeEmployeeRepository;
    @Mock
    CourierRepository courierRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getByIdShouldCallRepository() {
        userService.getById(Mockito.anyInt());

        Mockito.verify(userRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getAllShouldCallRepository() {
        userService.getAll(Optional.empty());

        Mockito.verify(userRepository, Mockito.times(1))
            .search(Optional.empty());
    }

    @Test
    void getByUsernameShouldCallRepository() {
        userService.getByUsername("test username");

        Mockito.verify(userRepository, Mockito.times(1))
            .getByField("username", "test username");
    }

    @Test
    void getEmployeeCompanyShouldCallRepository() {
        userService.getEmployeeCompany(1);

        Mockito.verify(userRepository, Mockito.times(1))
            .getEmployeeCompany(1);
    }

    @Test
    void createShouldCallRepository() {
        User user = new User();
        user.setId(1);
        user.setUsername("test username");
        Mockito.when(userRepository.getByField("username", user.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        userService.create(user);

        Mockito.verify(userRepository, Mockito.times(1))
            .create(Mockito.any());
    }

    @Test
    void createShouldThrowIfUserAlreadyExists() {
        Assertions.assertThrows(DuplicateEntityException.class, () -> userService.create(new User()));
    }

    @Test
    void createShouldAddUserRole() {
        User user = new User();
        user.setId(1);
        user.setUsername("test username");
        Mockito.when(userRepository.getByField("username", user.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        userService.create(user);

        Assertions.assertTrue(user.getRoles().contains(Role.USER));
    }

    @Test
    void updateShouldCallRepository() {
        userService.update(new User(), new User());

        Mockito.verify(userRepository, Mockito.times(1))
            .update(Mockito.any());
    }

    @Test
    void updateShouldCallValidateOwnerUpdate() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            userService.update(new User(), new User());

            mockedStatic.verify(() -> ValidationUtil.validateOwnerUpdate(Mockito.anyInt(), Mockito.anyInt()),
                                Mockito.times(1));
        }
    }

    @Test
    void deleteShouldCallRepository() {
        User user = new User();
        user.setId(1);

        userService.delete(user.getId(), user);

        Mockito.verify(userRepository, Mockito.times(1))
            .delete(user.getId());
    }

    @Test
    void deleteShouldCallValidateOwnerDelete() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            User user = new User();
            user.setId(1);

            userService.delete(user.getId(), user);

            mockedStatic.verify(() -> ValidationUtil.validateOwnerDelete(user.getId(), user),
                                Mockito.times(1));
        }
    }

    @Test
    void addRoleShouldNotCallValidateRoleUpdateIfRoleUser() {
        User user = new User();
        user.setId(1);
        userService = Mockito.spy(new UserServiceImpl(userRepository, null, null));

        userService.addRole(user, String.valueOf(Role.USER), UserMockData.createMockAdmin());

        Mockito.verify(userService, Mockito.times(0))
            .validateRoleUpdate(String.valueOf(Role.USER), UserMockData.createMockAdmin());
    }

    @Test
    void addRoleShouldCallValidateRoleUpdate() {
        User user = new User();
        user.setId(1);
        userService = Mockito.spy(new UserServiceImpl(userRepository, null, null));

        userService.addRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Mockito.verify(userService, Mockito.times(1))
            .validateRoleUpdate(String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());
    }

    @Test
    void addRoleShouldAddRoleToUser() {
        User user = new User();
        user.setId(1);

        Assertions.assertFalse(user.getRoles().contains(Role.EMPLOYEE));

        userService.addRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Assertions.assertTrue(user.getRoles().contains(Role.EMPLOYEE));
    }

    @Test
    void addRoleShouldCallRepository() {
        User user = new User();
        user.setId(1);

        userService.addRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Mockito.verify(userRepository, Mockito.times(1))
            .update(user);
    }

    @Test
    void removeRoleShouldCallValidateRoleUpdate() {
        User user = new User();
        user.setId(1);
        userService = Mockito.spy(new UserServiceImpl(userRepository, null, null));

        userService.removeRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Mockito.verify(userService, Mockito.times(1))
            .validateRoleUpdate(String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());
    }

    @Test
    void removeRoleShouldRemoveRoleFromUser() {
        User user = new User();
        user.setId(1);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.EMPLOYEE);
        user.setRoles(roles);

        Assertions.assertTrue(user.getRoles().contains(Role.EMPLOYEE));

        userService.removeRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Assertions.assertFalse(user.getRoles().contains(Role.EMPLOYEE));
    }

    @Test
    void removeRoleShouldCallRepository() {
        User user = new User();
        user.setId(1);

        userService.removeRole(user, String.valueOf(Role.EMPLOYEE), UserMockData.createMockAdmin());

        Mockito.verify(userRepository, Mockito.times(1))
            .update(user);
    }

    @Test
    void makeOfficeEmployeeShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(false);
            Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(new User());

            userService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(User.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeOfficeEmployeeShouldThrowIfCourierAlreadyEmployee() {
        Mockito.when(userRepository.isAlreadyEmployee(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> userService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin()));
    }

    @Test
    void makeOfficeEmployeeShouldThrowIfCourierAlreadyOfficeEmployee() {
        Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> userService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin()));
    }

    @Test
    void makeOfficeEmployeeShouldCallRepository() {
        Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(false);
        Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(new User());

        userService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin());

        Mockito.verify(userRepository, Mockito.times(1))
            .makeOfficeEmployee(1, 1);
    }

    @Test
    void makeOfficeEmployeeShouldAddUserRole() {
        User user = new User();
        Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(false);
        Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);

        Assertions.assertFalse(user.getRoles().contains(Role.EMPLOYEE));

        userService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin());

        Assertions.assertTrue(user.getRoles().contains(Role.EMPLOYEE));
    }

    @Test
    void makeCourierShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(false);
            Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(new User());

            userService.makeCourier(1, 1, UserMockData.createMockAdmin());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(User.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeCourierShouldThrowIfCourierAlreadyEmployee() {
        Mockito.when(userRepository.isAlreadyEmployee(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> userService.makeCourier(1, 1, UserMockData.createMockAdmin()));
    }

    @Test
    void makeCourierShouldThrowIfCourierAlreadyOfficeEmployee() {
        Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> userService.makeCourier(1, 1, UserMockData.createMockAdmin()));
    }

    @Test
    void makeCourierShouldCallRepository() {
        Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(false);
        Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(new User());

        userService.makeCourier(1, 1, UserMockData.createMockAdmin());

        Mockito.verify(userRepository, Mockito.times(1))
            .makeCourier(1, 1);
    }

    @Test
    void makeCourierShouldAddUserRole() {
        User user = new User();
        Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(false);
        Mockito.when(userRepository.getById(Mockito.anyInt())).thenReturn(user);

        Assertions.assertFalse(user.getRoles().contains(Role.EMPLOYEE));

        userService.makeCourier(1, 1, UserMockData.createMockAdmin());

        Assertions.assertTrue(user.getRoles().contains(Role.EMPLOYEE));
    }

    @Test
    void validateRoleUpdateShouldThrowIfUpdaterNotAdmin() {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.EMPLOYEE);
        user.setRoles(roles);
        Assertions.assertThrows(UnauthorizedOperationException.class,
                                () -> userService.validateRoleUpdate(Role.ADMIN.toString(), user));
    }

    @Test
    void validateRoleUpdateShouldThrowIfUpdaterDoesNotHaveRoles() {
        Assertions.assertThrows(UnauthorizedOperationException.class,
                                () -> userService.validateRoleUpdate(Role.ADMIN.toString(), new User()));
    }

    @Test
    void validateRoleUpdateShouldThrowIfRoleToBeAssignedNotExists() {
        Assertions.assertThrows(InvalidDataException.class,
                                () -> userService.validateRoleUpdate("BOSS", UserMockData.createMockAdmin()));
    }

}
