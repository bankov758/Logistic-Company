package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import com.nbu.logisticcompany.utils.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nbu.logisticcompany.utils.ValidationUtil.*;

@Service
public class OfficeEmployeeServiceImpl implements OfficeEmployeeService {

    private final OfficeEmployeeRepository officeEmployeeRepository;

    @Autowired
    public OfficeEmployeeServiceImpl(OfficeEmployeeRepository officeEmployeeRepository) {
        this.officeEmployeeRepository = officeEmployeeRepository;
    }

    @Override
    public List<OfficeEmployee> getAll(Optional<String> search) {
        return officeEmployeeRepository.getAll();
    }

    @Override
    public OfficeEmployee getById(int id) {
        return officeEmployeeRepository.getById(id);
    }

    @Override
    public OfficeEmployee getByUsername(String username) {
        return officeEmployeeRepository.getByField("username", username);
    }

    @Override
    public void create(OfficeEmployee officeEmployee) {
        if (officeEmployee.getOffice().getCompany().getId() != officeEmployee.getCompany().getId()){
            throw new InvalidDataException("Office employee company and office company do not match");
        }
        officeEmployeeRepository.create(officeEmployee);
    }

    @Override
    public void demoteToUser(int officeEmployeeToDemoteId, User updater) {
        validateAdminAction(updater, OfficeEmployee.class, Action.UPDATE);
        officeEmployeeRepository.removeUserFromOfficeEmployees(officeEmployeeToDemoteId);
    }

    @Override
    public void makeCourier(int officeEmployeeToUpdateId, User updater) {
        validateAdminAction(updater, OfficeEmployee.class, Action.UPDATE);
        officeEmployeeRepository.removeUserFromOfficeEmployees(officeEmployeeToUpdateId);
        officeEmployeeRepository.makeCourier(officeEmployeeToUpdateId);
    }

    @Override
    public void update(OfficeEmployee officeEmployeeToUpdate, User updater) {
        validateOwnerUpdate(officeEmployeeToUpdate.getId(), updater.getId());
        if (officeEmployeeToUpdate.getOffice().getCompany().getId() != officeEmployeeToUpdate.getCompany().getId()){
            throw new InvalidDataException("Office employee company and office company do not match");
        }
        officeEmployeeRepository.update(officeEmployeeToUpdate);
    }

    @Override
    public void delete(int id, User updater) {
        validateOwnerDelete(id, updater.getId());
        officeEmployeeRepository.delete(id);
    }

}
