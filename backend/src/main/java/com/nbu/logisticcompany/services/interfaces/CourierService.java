package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface CourierService {

    Courier getById(int id);

    List<Courier> getAll(Optional<String> search);

    Courier getCourierFromShipment(int shipmentId);

    void create(Courier courier);

    void update(Courier courierToUpdate, User updater);

    void delete(int courierToDeleteId, User deleter);

    void demoteToUser(int courierToDemoteId, User updater);

    void makeOfficeEmployee(int courierId, int officeId, User updater);

}
