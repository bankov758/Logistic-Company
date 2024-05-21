package com.nbu.logisticcompany.utils;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.exceptions.ValidationException;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ValidationUtilTests {

    @Mock
    CourierService courierService;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    ValidationUtil validationUtil;

    @Test
    public void testValidateMethodWithNoErrors() {
        BindingResult result = Mockito.mock(BindingResult.class);
        Mockito.when(result.hasErrors()).thenReturn(false);
        Assertions.assertDoesNotThrow(() -> ValidationUtil.validate(result));
    }

    @Test
    public void testValidateOwnerUpdateMethodAllowsUpdateByOwner() {
        int userToUpdateId = 1;
        int updaterId = 1;
        Assertions.assertDoesNotThrow(() -> ValidationUtil.validateOwnerUpdate(userToUpdateId, updaterId));
    }

    @Test
    public void testValidateOwnerDeleteMethodAllowsUserToDeleteOwnAccount() {
        int userToDeleteId = 1;
        User destroyer = new User();
        destroyer.setId(1);
        Assertions.assertDoesNotThrow(() -> ValidationUtil.validateOwnerDelete(userToDeleteId, destroyer));
    }

    @Test
    public void testValidateAdminActionMethodAllowsAdminToPerformActionOnEntity() {
        User admin = new User();
        admin.setRoles(Collections.singleton(Role.ADMIN));
        Class<User> entityClass = User.class;
        Action action = Action.CREATE;
        Assertions.assertDoesNotThrow(() -> ValidationUtil.validateAdminAction(admin, entityClass, action));
    }

    @Test
    public void testAuthorizeOfficeEmployeeActionMethodAllowsOfficeEmployeeToModifyEntity() {
        int entityCompanyId = 1;
        User user = new User();
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        user.setId(1);
        Class<User> entityClass = User.class;
        Mockito.when(courierService.getById(Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getEmployeeCompany(Mockito.anyInt())).thenReturn(new Company(entityCompanyId, null));

        Assertions.assertDoesNotThrow(() -> validationUtil.authorizeOfficeEmployeeAction(entityCompanyId, user, entityClass));
    }

    @Test
    public void testValidateMethodThrowsValidationExceptionWithValidationErrors() {
        BindingResult result = Mockito.mock(BindingResult.class);
        Mockito.when(result.hasErrors()).thenReturn(true);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("object", "field", "error message"));
        fieldErrors.add(new FieldError("object", "anotherField", "another error message"));
        Mockito.when(result.getFieldErrors()).thenReturn(fieldErrors);
        Assertions.assertThrows(ValidationException.class, () -> ValidationUtil.validate(result));
    }

    @Test
    public void testValidateOwnerUpdateMethodThrowsWhenUpdaterIsNotOwner() {
        int userToUpdateId = 1;
        int updaterId = 2;
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> ValidationUtil.validateOwnerUpdate(userToUpdateId, updaterId));
    }

    @Test
    public void testValidateOwnerDeleteMethodThrowsWhenDestroyerIsNotAuthorized() {
        int userToDeleteId = 1;
        User destroyer = new User();
        destroyer.setId(2);
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> ValidationUtil.validateOwnerDelete(userToDeleteId, destroyer));
    }

    @Test
    public void testValidateAdminActionMethodThrowsWhenUserIsNotAdminOrUnauthorized() {
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        Class<User> entityClass = User.class;
        Action action = Action.CREATE;
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> ValidationUtil.validateAdminAction(user, entityClass, action));
    }

    @Test
    public void testValidateAdminActionMethodThrowsWhenParametersAreNull() {
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> ValidationUtil.validateAdminAction(null, null, null));
    }

    @Test
    public void testAuthorizeOfficeEmployeeActionMethodThrowsWhenUserIsCourier() {
        int entityCompanyId = 1;
        User user = new User();
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        user.setId(1);
        Mockito.when(courierService.getById(user.getId())).thenReturn(new Courier());
        Class<User> entityClass = User.class;
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> validationUtil.authorizeOfficeEmployeeAction(entityCompanyId, user, entityClass));
    }

    @Test
    public void testAuthorizeOfficeEmployeeActionMethodThrowsWhenUserIsFromDifferentCompany() {
        int entityCompanyId = 1;
        User user = new User();
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        user.setId(1);
        Class<User> entityClass = User.class;
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> validationUtil.authorizeOfficeEmployeeAction(entityCompanyId, user, entityClass));
    }

    @Test
    public void testIsCourierReturnsTrueIfUserIsCourier() {
        User user = new User();
        user.setId(1);
        Mockito.when(courierService.getById(Mockito.anyInt())).thenReturn(new Courier());
        boolean result = validationUtil.isCourier(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsCourierReturnsFalseIfUserIsNotCourier() {
        User user = new User();
        user.setId(1);
        Mockito.when(courierService.getById(Mockito.anyInt())).thenThrow(new EntityNotFoundException(""));
        boolean result = validationUtil.isCourier(user);
        Assertions.assertFalse(result);
    }
}