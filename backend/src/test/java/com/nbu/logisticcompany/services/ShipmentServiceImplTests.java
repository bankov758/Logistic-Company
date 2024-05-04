package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.*;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.helpers.OfficeMockData;
import com.nbu.logisticcompany.helpers.ShipmentMockData;
import com.nbu.logisticcompany.helpers.UserMockData;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.nbu.logisticcompany.services.ShipmentServiceImpl.COURIER_COMPANY_DOES_NOT_MATCH;
import static com.nbu.logisticcompany.services.ShipmentServiceImpl.OFFICE_EMPLOYEE_COMPANY_DOES_NOT_MATCH;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTests {

    @Mock
    ShipmentRepository shipmentRepository;
    @Mock
    ValidationUtil validationUtil;
    @Mock
    TariffsService tariffsService;
    @Mock
    OfficeService officeService;

    @InjectMocks
    ShipmentServiceImpl shipmentService;

    @Test
    void getByIdShouldCallRepository() {
        shipmentService.getById(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getById(Mockito.anyInt());
    }

    @Test
    void getNotDeliveredShouldCallRepository() {
        shipmentService.getNotDelivered(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getNotDelivered(Mockito.anyInt());
    }

    @Test
    void getBySenderOrReceiverShouldCallRepository() {
        shipmentService.getBySenderOrReceiver(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getBySenderOrReceiver(Mockito.anyInt());
    }

    @Test
    void getByCompanyIdShouldCallRepository() {
        shipmentService.getByCompanyId(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getByCompanyId(Mockito.anyInt());
    }

    @Test
    void filterShouldCallRepository() {
        shipmentService.filter(Optional.empty(), Optional.empty(),
                               Optional.empty(), Optional.empty());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .filter(Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty());
    }

    @Test
    void getAllShouldCallRepository() {
        shipmentService.getAll();

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getAll();
    }

    @Test
    void getSenderShouldCallRepository() {
        shipmentService.getSender(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getSender(Mockito.anyInt());
    }

    @Test
    void getReceiverShouldCallRepository() {
        shipmentService.getReceiver(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getReceiver(Mockito.anyInt());
    }

    @Test
    void getEmployeeShouldCallRepository() {
        shipmentService.getEmployee(Mockito.anyInt());

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .getEmployee(Mockito.anyInt());
    }

    @Test
    void createShouldCallRepository() {
        User creator = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.create(mockShipment, creator);

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .create(mockShipment);
    }

    @Test
    void createShouldCallAuthorizeOfficeEmployeeAction() {
        User creator = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.create(mockShipment, creator);

        Mockito.verify(validationUtil, Mockito.times(1))
            .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), creator, Shipment.class);
    }

    @Test
    void updateShouldCallRepository() {
        User updater = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.update(mockShipment, updater);

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .update(mockShipment);
    }

    @Test
    void updateShouldCallAuthorizeOfficeEmployeeAction() {
        User updater = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.update(mockShipment, updater);

        Mockito.verify(validationUtil, Mockito.times(1))
            .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), updater, Shipment.class);
    }

    @Test
    void deleteShouldCallRepository() {
        User destroyer = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        Mockito.when(shipmentService.getById(mockShipment.getId())).thenReturn(mockShipment);

        shipmentService.delete(mockShipment.getId(), destroyer);

        Mockito.verify(shipmentRepository, Mockito.times(1))
            .delete(mockShipment.getId());
    }

    @Test
    void deleteShouldCallAuthorizeOfficeEmployeeAction() {
        User destroyer = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        Mockito.when(shipmentService.getById(mockShipment.getId())).thenReturn(mockShipment);

        shipmentService.delete(mockShipment.getId(), destroyer);

        Mockito.verify(validationUtil, Mockito.times(1))
            .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), destroyer, Shipment.class);
    }

    @Test
    void applyTariffIfShipmentSentFromOffice() {
        Tariff tariff = new Tariff();
        tariff.setOfficeDiscount(20);
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setSentFromOffice(true);
        mockShipment.setWeight(1000);
        Mockito.when(tariffsService.getByCompany(Mockito.anyInt())).thenReturn(tariff);

        shipmentService.applyTariff(mockShipment);

        Assertions.assertEquals(800, mockShipment.getPrice());
    }

    @Test
    void applyTariffIfShipmentSentAndReceivedFromOffice() {
        Tariff tariff = new Tariff();
        tariff.setOfficeDiscount(20);
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setSentFromOffice(true);
        mockShipment.setReceivedFromOffice(true);
        mockShipment.setWeight(1000);
        Mockito.when(tariffsService.getByCompany(Mockito.anyInt())).thenReturn(tariff);

        shipmentService.applyTariff(mockShipment);

        Assertions.assertEquals(600, mockShipment.getPrice());
    }

    @Test
    void applyTariffWithNonDefaultPricePerKgOffice() {
        Tariff tariff = new Tariff();
        tariff.setOfficeDiscount(20);
        tariff.setPricePerKG(5);
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setSentFromOffice(true);
        mockShipment.setWeight(1000);
        Mockito.when(tariffsService.getByCompany(Mockito.anyInt())).thenReturn(tariff);

        shipmentService.applyTariff(mockShipment);

        Assertions.assertEquals(4000, mockShipment.getPrice());
    }

    @Test
    void applyDefaultPricePerKgWhenTariffNotAvailable() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setSentFromOffice(true);
        mockShipment.setWeight(1000);

        shipmentService.applyTariff(mockShipment);

        Assertions.assertEquals(1000, mockShipment.getPrice());
    }

    @Test
    void populateOfficeAddressesShouldRaiseSentFromOfficeFlag() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Office departureOffice = OfficeMockData.createOffice();
        mockShipment.setDepartureAddress(departureOffice.getAddress());
        Mockito.when(officeService.filter(
                Optional.of(mockShipment.getDepartureAddress()),
                Optional.of(mockShipment.getCompany().getId()),
                Optional.empty()))
            .thenReturn(Collections.singletonList(departureOffice));

        shipmentService.populateOfficeAddresses(mockShipment);

        Assertions.assertTrue(mockShipment.isSentFromOffice());
    }

    @Test
    void populateOfficeAddressesShouldRaiseReceivedFromOfficeFlag() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Office arrivalOffice = OfficeMockData.createOffice();
        mockShipment.setArrivalAddress(arrivalOffice.getAddress());
        Mockito.when(officeService.filter(
                Optional.of(mockShipment.getArrivalAddress()),
                Optional.of(mockShipment.getCompany().getId()),
                Optional.empty()))
            .thenReturn(Collections.singletonList(arrivalOffice));

        shipmentService.populateOfficeAddresses(mockShipment);

        Assertions.assertTrue(mockShipment.isReceivedFromOffice());
    }

    @Test
    void validateCompanyShouldThrowWhenCourierCompanyDoesNotMatchShipment() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Courier mockCourier = UserMockData.createMockCourier();
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee();
        mockCourier.setCompany(new Company(2, "Not Matching Company"));
        mockShipment.setCourier(mockCourier);
        mockShipment.setEmployee(employee);

        Assertions.assertThrowsExactly(InvalidDataException.class,
                                       () -> shipmentService.validateCompany(mockShipment),
                                       COURIER_COMPANY_DOES_NOT_MATCH);
    }

    @Test
    void validateCompanyShouldThrowWhenOfficeEmployeeCompanyDoesNotMatchShipment() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Courier mockCourier = UserMockData.createMockCourier();
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee();
        employee.setCompany(new Company(2, "Not Matching Company"));
        mockShipment.setCourier(mockCourier);
        mockShipment.setEmployee(employee);

        Assertions.assertThrowsExactly(InvalidDataException.class,
                                       () -> shipmentService.validateCompany(mockShipment),
                                       OFFICE_EMPLOYEE_COMPANY_DOES_NOT_MATCH);
    }

    @Test
    void validateCompanyShouldNotThrowWhenAllCompaniesMatch() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Courier mockCourier = UserMockData.createMockCourier();
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee();
        mockShipment.setCourier(mockCourier);
        mockShipment.setEmployee(employee);

        Assertions.assertDoesNotThrow(() -> shipmentService.validateCompany(mockShipment));
    }

}
