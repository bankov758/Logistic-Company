package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(int id);

    User getByUsername(String username);

    List<User> getAll(Optional<String> search);

    Company getEmployeeCompany(int employeeId);

    User create(User user);

    void update(User userToUpdate, User updater);

    void addRole(User user, String role, User updater);

    void removeRole(User user, String role, User updater);

    void delete(int id, User user);

}
