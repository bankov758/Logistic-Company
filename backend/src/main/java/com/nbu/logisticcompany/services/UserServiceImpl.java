package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.services.interfaces.UserService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String UNAUTHORIZED_UPDATE = "Only the owner of the account can make changes";
    private static final String UNAUTHORIZED_ROLE_UPDATE = "Only an admin account can change roles";
    private static final String UNAUTHORIZED_DELETE = "Only the owner of the account can delete it";

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
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        userRepository.create(user);
    }

    @Override
    public void update(User userToUpdate, User updater) {
        if (userToUpdate.getId() != updater.getId()) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
        }
        userRepository.update(userToUpdate);
    }

    @Override
    public void addRole(User user, String role, User updater) {
        validateRoleUpdate(role, updater);
        user.getRoles().add(Role.valueOf(role));
        userRepository.update(user);
    }

    @Override
    public void removeRole(User user, String role, User updater) {
        validateRoleUpdate(role, updater);
        user.getRoles().remove(Role.valueOf(role));
        userRepository.update(user);
    }

    @Override
    public void delete(int id, User user) {
        if (user.getId() != id) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
        }
        userRepository.delete(id);
    }

    private void validateRoleUpdate(String role, User updater){
        if (ValidationUtil.isNotEmpty(updater.getRoles()) && !updater.getRoles().contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_ROLE_UPDATE);
        }
        if (Arrays.stream(Role.values()).noneMatch(value -> value.toString().equals(role))){
            throw new InvalidDataException(User.class.getSimpleName(), "role", role);
        }
    }

}
