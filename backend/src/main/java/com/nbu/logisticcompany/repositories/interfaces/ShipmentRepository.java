package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Shipment;

import java.util.List;

public interface ShipmentRepository extends BaseCRUDRepository<Shipment> {

    Shipment getBySenderId(int senderId);

    Shipment getByReceiverId(int receiverId);

    Shipment getByEmployeeId(int employeeId);

    List<Shipment> getNotDelivered(int companyId);

    List<Shipment> getBySenderOrReceiver(int userId);

}
