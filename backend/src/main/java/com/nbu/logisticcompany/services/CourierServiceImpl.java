package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.utils.Action;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nbu.logisticcompany.utils.ValidationUtil.validateAdminAction;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Courier getById(int id) {
        return courierRepository.getById(id);
    }

    @Override
    public Courier getByUsername(String username) {
        return courierRepository.getByField("username", username);
    }

    @Override
    public List<Courier> getAll(Optional<String> search) {
        return courierRepository.getAll();
    }

    @Override
    public Courier getCourierFromShipment(int shipmentId) {
        return courierRepository.getCourierFromShipment(shipmentId);
    }

    @Override
    public void create(Courier courier) {
        courierRepository.create(courier);
    }

    @Override
    public void update(Courier courierToUpdate, User updater) {
        ValidationUtil.validateOwnerUpdate(courierToUpdate.getId(), updater.getId());
        courierRepository.update(courierToUpdate);
    }

    @Override
    public void delete(int courierToDeleteId, User deleter) {
        ValidationUtil.validateOwnerDelete(courierToDeleteId, deleter);
        courierRepository.delete(courierToDeleteId);
    }

    @Override
    public void demoteToUser(int courierToDemoteId, User updater) {
        validateAdminAction(updater, Courier.class, Action.UPDATE);
        courierRepository.removeUserFromCouriers(courierToDemoteId);
    }

    @Override
    public void makeOfficeEmployee(int courierId, int officeId, User updater) {
        validateAdminAction(updater, OfficeEmployee.class, Action.UPDATE);
        courierRepository.removeUserFromCouriers(courierId);
        courierRepository.makeOfficeEmployee(courierId, officeId);
    }

}
