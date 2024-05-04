package com.nbu.logisticcompany.services;

import java.util.Optional;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.helpers.OfficeMockData;
import com.nbu.logisticcompany.helpers.UserMockData;
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
class OfficeEmployeeServiceImplTests {

    @Mock
    OfficeEmployeeRepository officeEmployeeRepository;
    @Mock
    CourierRepository courierRepository;

    @InjectMocks
    OfficeEmployeeServiceImpl officeEmployeeService;

    @Test
    void getByIdShouldCallRepository() {
        officeEmployeeService.getById(Mockito.anyInt());

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getAllShouldCallRepository() {
        officeEmployeeService.getAll(Optional.empty());

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .getAll();
    }

    @Test
    void getByUsernameShouldCallRepository() {
        officeEmployeeService.getByUsername("test username");

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .getByField("username", "test username");
    }

    @Test
    void createShouldCallRepository() {
        Company company = new Company(1, "Test Company 1");
        Office office = OfficeMockData.createOffice(company);
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee(company);
        employee.setOffice(office);

        officeEmployeeService.create(employee);

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .create(Mockito.any());
    }

    @Test
    void createShouldThrowIfEmployeeAndOfficeCompanyMismatch() {
        Company company = new Company(1, "Test Company 1");
        Company company2 = new Company(2, "Test Company 2");
        Office office = OfficeMockData.createOffice(company);
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee(company2);
        employee.setOffice(office);

        Assertions.assertThrows(InvalidDataException.class,
                                () -> officeEmployeeService.create(employee));
    }

    @Test
    void updateShouldCallValidateOwnerUpdate() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            Company company = new Company(1, "Test Company 1");
            Office office = OfficeMockData.createOffice(company);
            OfficeEmployee employee = UserMockData.createMockOfficeEmployee(company);
            employee.setOffice(office);

            officeEmployeeService.update(employee, new User());

            mockedStatic.verify(() -> ValidationUtil.validateOwnerUpdate(Mockito.anyInt(), Mockito.anyInt()),
                                Mockito.times(1));
        }
    }

    @Test
    void updateShouldThrowIfEmployeeAndOfficeCompanyMismatch() {
        Company company = new Company(1, "Test Company 1");
        Company company2 = new Company(2, "Test Company 2");
        Office office = OfficeMockData.createOffice(company);
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee(company2);
        employee.setOffice(office);

        Assertions.assertThrows(InvalidDataException.class,
                                () -> officeEmployeeService.update(employee, new User()));
    }

    @Test
    void deleteShouldCallValidateOwnerUpdate() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            officeEmployeeService.delete(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateOwnerDelete(1, new User()),
                                Mockito.times(1));
        }
    }

    @Test
    void demoteToUserShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            officeEmployeeService.demoteToUser(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(OfficeEmployee.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeCourierShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(false);

            officeEmployeeService.makeCourier(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(OfficeEmployee.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void makeCourierShouldThrowIfCourierAlreadyCourier() {
        Mockito.when(courierRepository.isAlreadyCourier(Mockito.anyInt())).thenReturn(true);

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> officeEmployeeService.makeCourier(1, UserMockData.createMockUser(Role.ADMIN)));
    }

    @Test
    void makeCourierShouldCallRemoveUserFromOfficeEmployee() {
        officeEmployeeService.makeCourier(1, UserMockData.createMockUser(Role.ADMIN));

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .removeUserFromOfficeEmployees(Mockito.anyInt());
    }

    @Test
    void makeOfficeEmployeeShouldCallRepository() {
        officeEmployeeService.makeCourier(1, UserMockData.createMockUser(Role.ADMIN));

        Mockito.verify(officeEmployeeRepository, Mockito.times(1))
            .makeCourier(Mockito.anyInt());
    }

}
