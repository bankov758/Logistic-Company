package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface OfficeEmployeeService {

    OfficeEmployee getById(int id);

    OfficeEmployee getByUsername(String username);

    List<OfficeEmployee> getAll(Optional<String> search);

    void create(OfficeEmployee officeEmployee);

    void demoteToUser(int officeEmployeeToDemoteId, User updater);

    void makeCourier(int officeEmployeeToUpdateId, User updater);

    void update(OfficeEmployee officeEmployeeToUpdate, User updater);

    void delete(int officeEmployeeToDeleteId, User deleter);

}
