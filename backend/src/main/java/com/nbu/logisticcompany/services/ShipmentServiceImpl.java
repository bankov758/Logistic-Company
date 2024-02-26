package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final TariffsService tariffsService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, TariffsService tariffsService,
                               ValidationUtil validationUtil) {
        this.shipmentRepository = shipmentRepository;
        this.tariffsService = tariffsService;
        this.validationUtil = validationUtil;
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
    public  List<Shipment> getNotDelivered(int companyId) {
        return shipmentRepository.getNotDelivered( companyId);
    }

    @Override
    public List<Shipment> getAll() {
        return shipmentRepository.getAll();
    }

    @Override
    public void create(Shipment shipment, User creator) {
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), creator, Shipment.class);
        applyTariff(shipment);
        shipmentRepository.create(shipment);
    }

    @Override
    public void update(Shipment shipmentToUpdate, User updater) {
        validationUtil.authorizeOfficeEmployeeAction(shipmentToUpdate.getCompany().getId(), updater, Shipment.class);
        applyTariff(shipmentToUpdate);
        shipmentRepository.update(shipmentToUpdate);
    }

    @Override
    public void delete(int shipmentId, User destroyer) {
        Shipment shipment = shipmentRepository.getById(shipmentId);
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), destroyer, Shipment.class);
        shipmentRepository.delete(shipmentId);
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

}
