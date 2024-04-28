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
public class ShipmentServiceImplTests {

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
    public void getByIdShouldCallRepository() {
        shipmentService.getById(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getById(1);
    }

    @Test
    public void getNotDeliveredShouldCallRepository() {
        shipmentService.getNotDelivered(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getNotDelivered(1);
    }

    @Test
    public void getBySenderOrReceiverShouldCallRepository() {
        shipmentService.getBySenderOrReceiver(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getBySenderOrReceiver(1);
    }

    @Test
    public void getByCompanyIdShouldCallRepository() {
        shipmentService.getByCompanyId(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getByCompanyId(1);
    }

    @Test
    public void filterShouldCallRepository() {
        shipmentService.filter(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty());

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .filter(Optional.empty(), Optional.empty(),
                        Optional.empty(), Optional.empty());
    }

    @Test
    public void getAllShouldCallRepository() {
        shipmentService.getAll();

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getAll();
    }

    @Test
    public void getSenderShouldCallRepository() {
        shipmentService.getSender(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getSender(1);
    }

    @Test
    public void getReceiverShouldCallRepository() {
        shipmentService.getReceiver(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getReceiver(1);
    }

    @Test
    public void getEmployeeShouldCallRepository() {
        shipmentService.getEmployee(1);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .getEmployee(1);
    }

    @Test
    public void createShouldCallRepository() {
        User creator = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.create(mockShipment, creator);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .create(mockShipment);
    }

    @Test
    public void createShouldCallAuthorizeOfficeEmployeeAction() {
        User creator = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.create(mockShipment,creator);

        Mockito.verify(validationUtil, Mockito.times(1))
                .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), creator, Shipment.class);
    }

    @Test
    public void updateShouldCallRepository() {
        User updater = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.update(mockShipment, updater);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .update(mockShipment);
    }

    @Test
    public void updateShouldCallAuthorizeOfficeEmployeeAction() {
        User updater = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setEmployee(UserMockData.createMockOfficeEmployee());
        mockShipment.setCourier(UserMockData.createMockCourier());

        shipmentService.update(mockShipment, updater);

        Mockito.verify(validationUtil, Mockito.times(1))
                .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), updater, Shipment.class);
    }

    @Test
    public void deleteShouldCallRepository() {
        User destroyer = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        Mockito.when(shipmentService.getById(mockShipment.getId())).thenReturn(mockShipment);

        shipmentService.delete(mockShipment.getId(), destroyer);

        Mockito.verify(shipmentRepository, Mockito.times(1))
                .delete(mockShipment.getId());
    }

    @Test
    public void deleteShouldCallAuthorizeOfficeEmployeeAction() {
        User destroyer = UserMockData.createMockEmployee();
        Shipment mockShipment = ShipmentMockData.createShipment();
        Mockito.when(shipmentService.getById(mockShipment.getId())).thenReturn(mockShipment);

        shipmentService.delete(mockShipment.getId(), destroyer);

        Mockito.verify(validationUtil, Mockito.times(1))
                .authorizeOfficeEmployeeAction(mockShipment.getCompany().getId(), destroyer, Shipment.class);
    }

    @Test
    public void applyTariffIfShipmentSentFromOffice() {
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
    public void applyTariffIfShipmentSentAndReceivedFromOffice() {
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
    public void applyTariffWithNonDefaultPricePerKgOffice() {
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
    public void applyDefaultPricePerKgWhenTariffNotAvailable() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        mockShipment.setSentFromOffice(true);
        mockShipment.setWeight(1000);

        shipmentService.applyTariff(mockShipment);

        Assertions.assertEquals(1000, mockShipment.getPrice());
    }

    @Test
    public void populateOfficeAddressesShouldRaiseSentFromOfficeFlag() {
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
    public void populateOfficeAddressesShouldRaiseReceivedFromOfficeFlag() {
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
    public void validateCompanyShouldThrowWhenCourierCompanyDoesNotMatchShipment() {
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
    public void validateCompanyShouldThrowWhenOfficeEmployeeCompanyDoesNotMatchShipment() {
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
    public void validateCompanyShouldNotThrowWhenAllCompaniesMatch() {
        Shipment mockShipment = ShipmentMockData.createShipment();
        Courier mockCourier = UserMockData.createMockCourier();
        OfficeEmployee employee = UserMockData.createMockOfficeEmployee();
        mockShipment.setCourier(mockCourier);
        mockShipment.setEmployee(employee);

        Assertions.assertDoesNotThrow(() -> shipmentService.validateCompany(mockShipment));
    }

}
