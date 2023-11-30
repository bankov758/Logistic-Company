package com.nbu.logisticcompany.services.interfaces;

import com.nbu.logisticcompany.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getById(int id);

    User getByUsername(String username);

    List<User> getAll(Optional<String> search);

    void create(User user);

    void update(User userToUpdate, User user);

    void delete(int id, User user);

}
