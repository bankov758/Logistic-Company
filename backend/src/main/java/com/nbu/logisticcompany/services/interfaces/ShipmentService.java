package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.ShipmentCreateDto;
import com.nbu.logisticcompany.entities.dto.ShipmentUpdateDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ShipmentService {

    Shipment getById(int id);

    Shipment getByDepartureAddress(String departureAddress);

    Shipment getByArrivalAddress(String arrivalAddress);

    Shipment getBySenderId(int senderId);

    Shipment getByReceiverId(int receiverId);

    Shipment getByEmployeeId(int employeeId);

    List<Shipment> getAll() ;

    void create(Shipment shipment, User creator) throws IOException;

    void update(Shipment shipment, User user);

    void delete(int shipmentId, User user);
}
