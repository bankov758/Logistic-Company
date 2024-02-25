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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private static final String UNAUTHORIZED_SHIPMENT_ACTION = "Only office employees from the same company as the shipment can modify it";

    private final ShipmentRepository shipmentRepository;
    private final CourierService courierService;
    private final UserRepository userRepository;
    private final TariffsService tariffsService;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, CourierService courierService,
                               UserRepository userRepository, TariffsService tariffsService) {
        this.shipmentRepository = shipmentRepository;
        this.courierService = courierService;
        this.userRepository = userRepository;
        this.tariffsService = tariffsService;
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
        applyTariff(shipment);
        shipmentRepository.create(shipment);
    }

    @Override
    public void update(Shipment shipmentToUpdate, User updater) {
        authorizeShipmentAction(shipmentToUpdate.getCompany().getId(), updater);
        applyTariff(shipmentToUpdate);
        shipmentRepository.update(shipmentToUpdate);
    }

    @Override
    public void delete(int shipmentId, User destroyer) {
        Shipment shipment = shipmentRepository.getById(shipmentId);
        authorizeShipmentAction(shipment.getCompany().getId(), destroyer);
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

    private void authorizeShipmentAction(int shipmentCompanyId, User user) {
        Set<Role> userRoles = user == null || user.getRoles() == null ? Collections.emptySet() : user.getRoles();
        if (!userRoles.contains(Role.EMPLOYEE)
                || courierService.getById(user.getId()) != null
                || userRepository.getEmployeeCompany(user.getId()).getId() != shipmentCompanyId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_SHIPMENT_ACTION);
        }
    }

}
