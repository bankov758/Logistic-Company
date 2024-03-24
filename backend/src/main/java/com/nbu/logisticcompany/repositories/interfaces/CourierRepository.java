package com.nbu.logisticcompany.repositories.interfaces;

import com.nbu.logisticcompany.entities.Courier;

public interface CourierRepository extends BaseCRUDRepository<Courier> {

    Courier getCourierFromShipment(int shipmentId);

    void removeUserFromCouriers(int courierToDemoteId);

    void makeOfficeEmployee(int courierId, int officeId);

}
