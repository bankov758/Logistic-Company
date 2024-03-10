package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;

import java.io.IOException;
import java.util.List;

public interface ShipmentService {

    Shipment getById(int id);

    Shipment getByDepartureAddress(String departureAddress);

    Shipment getByArrivalAddress(String arrivalAddress);

    Shipment getBySenderId(int senderId);

    Shipment getByReceiverId(int receiverId);

    Shipment getByEmployeeId(int employeeId);

    List<Shipment> getNotDelivered(int companyId);

    List<Shipment> getBySenderOrReceiver(int userId);

    List<Shipment> getByCompanyId(int companyId);

    List<Shipment> getAll() ;

    void create(Shipment shipment, User creator) throws IOException;

    void update(Shipment shipment, User updater);

    void delete(int shipmentId, User destroyer);
}
