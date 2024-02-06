package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private  final ShipmentRepository shipmentRepository;
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment getById(int id) {
        return null;
    }

    @Override
    public Shipment getByDepartureAddress(String departureAddress) {
        return null;
    }

    @Override
    public Shipment getByArrivalAddress(String arrivalAddress) {
        return null;
    }

    @Override
    public Shipment getBySenderId(int senderId) {
        return null;
    }

    @Override
    public Shipment getByReceiverId(int receiverId) {
        return null;
    }

    @Override
    public Shipment getByEmployeeId(int employeeId) {
        return null;
    }

    @Override
    public List<Shipment> getAll() {
        return null;
    }

    @Override
    public void create(Shipment shipment) {
        boolean duplicateShipment = true;
        try {
            shipmentRepository.getByField("id", shipment.getId());
        } catch (EntityNotFoundException e) {
            duplicateShipment = false;
        }
        if (duplicateShipment) {
            //throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        shipmentRepository.create(shipment);
    }

    @Override
    public void update(Shipment shipmentToUpdate, User user) {
 /* if (companyToUpdate.getId() != user.getId()) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
        }*/
        shipmentRepository.update(shipmentToUpdate);
    }

    @Override
    public void delete(int shipmentId, User user) {
          /* if (user.getId() != shipmentID) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
        }*/
        shipmentRepository.delete(shipmentId);
    }
}
