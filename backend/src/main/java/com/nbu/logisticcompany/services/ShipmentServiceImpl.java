package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.*;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final TariffsService tariffsService;
    private final ValidationUtil validationUtil;
    private final OfficeService officeService;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, TariffsService tariffsService,
                               ValidationUtil validationUtil, OfficeService officeService) {
        this.shipmentRepository = shipmentRepository;
        this.tariffsService = tariffsService;
        this.validationUtil = validationUtil;
        this.officeService = officeService;
    }

    @Override
    public Shipment getById(int id) {
        return shipmentRepository.getById(id);
    }

    @Override
    public Shipment getByDepartureAddress(String departureAddress) {
        return shipmentRepository.getByField("departureAddress", departureAddress);
    }

    @Override
    public Shipment getByArrivalAddress(String arrivalAddress) {
        return shipmentRepository.getByField("arrivalAddress", arrivalAddress);
    }

    @Override
    public Shipment getBySenderId(int senderId) {
        return shipmentRepository.getBySenderId(senderId);
    }

    @Override
    public Shipment getByReceiverId(int receiverId) {
        return shipmentRepository.getByReceiverId(receiverId);
    }

    @Override
    public Shipment getByEmployeeId(int employeeId) {
        return shipmentRepository.getByEmployeeId(employeeId);
    }

    @Override
    public List<Shipment> getNotDelivered(int companyId) {
        return shipmentRepository.getNotDelivered(companyId);
    }

    @Override
    public List<Shipment> getAll() {
        return shipmentRepository.getAll();
    }

    @Override
    public void create(Shipment shipment, User creator) {
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), creator, Shipment.class);
        validateCompany(shipment);
        populateOfficeAddresses(shipment);
        applyTariff(shipment);
        shipmentRepository.create(shipment);
    }

    @Override
    public void update(Shipment shipmentToUpdate, User updater) {
        validationUtil.authorizeOfficeEmployeeAction(shipmentToUpdate.getCompany().getId(), updater, Shipment.class);
        validateCompany(shipmentToUpdate);
        populateOfficeAddresses(shipmentToUpdate);
        applyTariff(shipmentToUpdate);
        shipmentRepository.update(shipmentToUpdate);
    }

    @Override
    public void delete(int shipmentId, User destroyer) {
        Shipment shipment = shipmentRepository.getById(shipmentId);
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), destroyer, Shipment.class);
        shipmentRepository.delete(shipmentId);
    }

    private void validateCompany(Shipment shipment) {
        int shipmentCompanyId = shipment.getCompany().getId();
        int courierCompanyId = shipment.getCourier().getCompany().getId();
        int officeEmployeeCompanyId = shipment.getEmployee().getCompany().getId();
        if (shipmentCompanyId != courierCompanyId) {
            throw new InvalidDataException("Shipment and courier companies do not match");
        }
        if (shipmentCompanyId != officeEmployeeCompanyId) {
            throw new InvalidDataException("Shipment and office employee companies do not match");
        }
    }

    private void applyTariff(Shipment shipment) {
        Tariff tariff = tariffsService.getByCompany(shipment.getCompany().getId());
        double discountMultiplier = 1;
        if (shipment.isSentFromOffice() && shipment.isReceivedFromOffice()) {
            discountMultiplier = 2 * (tariff.getOfficeDiscount() / 100);
        } else if (shipment.isSentFromOffice() || shipment.isReceivedFromOffice()) {
            discountMultiplier = tariff.getOfficeDiscount() / 100;
        }
        double shipmentPrice = shipment.getWeight() * tariff.getPricePerKG() - shipment.getWeight() * tariff.getPricePerKG() * discountMultiplier;
        shipment.setPrice(shipmentPrice);
    }

    private void populateOfficeAddresses(Shipment shipment) {
        Company company = shipment.getCompany();
        if (shipment.getDepartureAddress() != null) {
            List<Office> offices = officeService.filter(Optional.of(shipment.getDepartureAddress()),
                    Optional.of(company.getId()), Optional.empty());
            if (ValidationUtil.isNotEmpty(offices)) {
                shipment.setSentFromOffice(true);
            }
        }
        if (shipment.getArrivalAddress() != null) {
            List<Office> offices = officeService.filter(Optional.of(shipment.getArrivalAddress()),
                    Optional.of(company.getId()), Optional.empty());
            if (ValidationUtil.isNotEmpty(offices)) {
                shipment.setReceivedFromOffice(true);
            }
        }
    }

}
