package com.nbu.logisticcompany.services;

import java.util.Collections;
import java.util.Optional;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.helpers.OfficeMockData;
import com.nbu.logisticcompany.helpers.UserMockData;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
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
class OfficeServiceImpTests {

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    OfficeServiceImpl officeService;

    @Test
    void getByIdShouldCallRepository() {
        officeService.getById(Mockito.anyInt());

        Mockito.verify(officeRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getByAddressShouldCallRepository() {
        officeService.getByAddress("Test Address");

        Mockito.verify(officeRepository, Mockito.times(1))
            .getByField("address", "Test Address");
    }

    @Test
    void filterShouldCallRepository() {
        officeService.filter(Optional.empty(), Optional.empty(), Optional.empty());

        Mockito.verify(officeRepository, Mockito.times(1))
            .filter(Optional.empty(), Optional.empty(), Optional.empty());
    }

    @Test
    void getAllShouldCallRepository() {
        officeService.getAll();

        Mockito.verify(officeRepository, Mockito.times(1)).getAll();
    }

    @Test
    void createShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            officeService.create(OfficeMockData.createOffice(new Company(1, "company")), new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Office.class),
                                                                         Mockito.eq(Action.CREATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void createShouldThrowIfOfficeAlreadyExists() {
        Office office = OfficeMockData.createOffice(new Company(1, "company"));
        Mockito.when(officeRepository.filter(Optional.ofNullable(office.getAddress()),
                                             Optional.of(office.getCompany().getId()), Optional.empty()))
            .thenReturn(Collections.singletonList(new Office()));

        Assertions.assertThrows(DuplicateEntityException.class,
                                () -> officeService.create(office, UserMockData.createMockAdmin()));
    }

    @Test
    void createShouldCallRepository() {
        Office office = OfficeMockData.createOffice(new Company(1, "company"));

        officeService.create(office, UserMockData.createMockAdmin());

        Mockito.verify(officeRepository, Mockito.times(1)).create(office);
    }

    @Test
    void updateShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            officeService.update(OfficeMockData.createOffice(new Company(1, "company")), new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Office.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void updateShouldCallRepository() {
        Office office = OfficeMockData.createOffice(new Company(1, "company"));

        officeService.update(office, UserMockData.createMockAdmin());

        Mockito.verify(officeRepository, Mockito.times(1)).update(office);
    }

    @Test
    void deleteShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            officeService.delete(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Office.class),
                                                                         Mockito.eq(Action.DELETE)),
                                Mockito.times(1));
        }
    }

    @Test
    void deleteShouldCallRepository() {
        officeService.delete(1, UserMockData.createMockAdmin());

        Mockito.verify(officeRepository, Mockito.times(1)).delete(1);
    }

}
