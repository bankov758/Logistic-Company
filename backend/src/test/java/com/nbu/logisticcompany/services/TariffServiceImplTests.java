package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.mock.TariffMockData;
import com.nbu.logisticcompany.mock.UserMockData;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import com.nbu.logisticcompany.utils.Action;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TariffServiceImplTests {

    @Mock
    TariffsRepository tariffsRepository;

    @InjectMocks
    TariffServiceImpl tariffService;

    @Test
    void getByIdShouldCallRepository() {
        tariffService.getById(Mockito.anyInt());

        Mockito.verify(tariffsRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getByCompanyShouldCallRepository() {
        tariffService.getByCompany(Mockito.anyInt());

        Mockito.verify(tariffsRepository, Mockito.times(1))
            .getByCompany(Mockito.anyInt());
    }

    @Test
    void getAllShouldCallRepository() {
        tariffService.getAll();

        Mockito.verify(tariffsRepository, Mockito.times(1)).getAll();
    }

    @Test
    void createShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            tariffService.create(TariffMockData.createTariff(), new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Tariff.class),
                                                                         Mockito.eq(Action.CREATE)),
                                Mockito.times(1));
        }
    }

//    @Test
//    void createShouldThrowIfTariffAlreadyExists() {
//        Assertions.assertThrows(DuplicateEntityException.class,
//                                () -> tariffService.create(TariffMockData.createTariff(), UserMockData.createMockAdmin()));
//    }

    @Test
    void createShouldCallRepository() {
        tariffService.create(TariffMockData.createTariff(), UserMockData.createMockAdmin());

        Mockito.verify(tariffsRepository, Mockito.times(1))
            .create(TariffMockData.createTariff());
    }

    @Test
    void updateShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            tariffService.update(TariffMockData.createTariff(), new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Tariff.class),
                                                                         Mockito.eq(Action.UPDATE)),
                                Mockito.times(1));
        }
    }

    @Test
    void updateShouldCallRepository() {
        tariffService.update(TariffMockData.createTariff(), UserMockData.createMockAdmin());

        Mockito.verify(tariffsRepository, Mockito.times(1))
            .update(TariffMockData.createTariff());
    }

    @Test
    void deleteShouldCallValidateAdminAction() {
        try (MockedStatic<ValidationUtil> mockedStatic = Mockito.mockStatic(ValidationUtil.class)) {
            tariffService.delete(1, new User());

            mockedStatic.verify(() -> ValidationUtil.validateAdminAction(Mockito.any(User.class),
                                                                         Mockito.eq(Tariff.class),
                                                                         Mockito.eq(Action.DELETE)),
                                Mockito.times(1));
        }
    }

    @Test
    void deleteShouldCallRepository() {
        tariffService.delete(1, UserMockData.createMockAdmin());

        Mockito.verify(tariffsRepository, Mockito.times(1)).delete(1);
    }

}
