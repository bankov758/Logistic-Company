package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByField("username", username);
    }

    @Override
    public List<User> getAll(Optional<String> search) {
        return userRepository.getAll();
    }

    @Override
    public void create(User user) {
        boolean duplicateUser = true;
        try {
            userRepository.getByField("username", user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateUser = false;
        }
        if (duplicateUser) {
            //throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        userRepository.create(user);
    }

    @Override
    public void update(User userToUpdate, User user) {
        if (userToUpdate.getId() != user.getId()) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
        }
        userRepository.update(userToUpdate);
    }

    @Override
    public void delete(int id, User user) {
        if (user.getId() != id) {
            //throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
        }
        userRepository.delete(id);
    }

}
