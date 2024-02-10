package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Shipment;

public interface ShipmentRepository extends  BaseCRUDRepository<Shipment> {

    Shipment getBySenderId(int senderId);

    Shipment getByReceiverId(int receiverId);

    Shipment getByEmployeeId(int employeeId);

}
