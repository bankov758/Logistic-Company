package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
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
    public List<Shipment> getAll() {
        return shipmentRepository.getAll();
    }

    @Override
    public void create(Shipment shipment, User creator) {
        //        if (user.getId() != shipmentID) {
//            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
//        }

        shipmentRepository.create(shipment);
    }

    @Override
    public void update(Shipment shipmentToUpdate, User user) {
//        if (companyToUpdate.getId() != user.getId()) {
//            throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
//        }
        shipmentRepository.update(shipmentToUpdate);
    }

    @Override
    public void delete(int shipmentId, User user) {
//        if (user.getId() != shipmentID) {
//            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
//        }
        shipmentRepository.delete(shipmentId);
    }

}
