package com.nbu.logisticcompany.services;

import java.util.Optional;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.mock.UserMockData;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
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
class CourierServiceImplTests {

    @Mock
    CourierRepository courierRepository;
    @Mock
    OfficeEmployeeRepository officeEmployeeRepository;

    @InjectMocks
    CourierServiceImpl courierService;

    @Test
    void getByIdShouldCallRepository() {
        courierService.getById(Mockito.anyInt());

        Mockito.verify(courierRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getAllShouldCallRepository() {
        courierService.getAll(Optional.empty());

        Mockito.verify(courierRepository, Mockito.times(1))
            .getAll();
    }

    @Test
    void getCourierFromShipmentShouldCallRepository() {
        courierService.getCourierFromShipment(Mockito.anyInt());

        Mockito.verify(courierRepository, Mockito.times(1))
            .getCourierFromShipment(Mockito.anyInt());
    }

    @Test
    void createShouldCallRepository() {
        courierService.create(new Courier());

        Mockito.verify(courierRepository, Mockito.times(1))
            .create(Mockito.any());
    }

    @Test
    void updateShouldCallValidateOwnerUpdate() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            courierService.update(new Courier(), new User());

            mockedStatic.verify(() -> ValidationUtil.validateOwnerUpdate(Mockito.anyInt(), Mockito.anyInt()),
                                Mockito.times(1));
        }
    }

    @Test
    void deleteShouldCallValidateOwnerUpdate() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            courierService.delete(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateOwnerDelete(1, new User()),
                                Mockito.times(1));
        }
    }

    @Test
    void demoteToUserShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            courierService.demoteToUser(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Courier.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeOfficeEmployeeShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(false);

            courierService.makeOfficeEmployee(1, 1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(OfficeEmployee.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeOfficeEmployeeShouldThrowIfCourierAlreadyOfficeEmployee() {
        Mockito.when(officeEmployeeRepository.isAlreadyOfficeEmployee(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> courierService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin()));
    }

    @Test
    void makeOfficeEmployeeShouldCallRemoveUserFromCouriers() {
        courierService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin());

        Mockito.verify(courierRepository, Mockito.times(1))
            .removeUserFromCouriers(Mockito.anyInt());
    }

    @Test
    void makeOfficeEmployeeShouldCallRepository() {
        courierService.makeOfficeEmployee(1, 1, UserMockData.createMockAdmin());

        Mockito.verify(courierRepository, Mockito.times(1))
            .makeOfficeEmployee(Mockito.anyInt(), Mockito.anyInt());
    }

}
