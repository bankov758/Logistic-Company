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

    private static final String UNAUTHORIZED_ROLE_UPDATE = "Only an admin account can change roles";

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
    public User create(User user) {
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
        User alreadySaved = getById(user.getId());
        addRole(alreadySaved, Role.USER.name(), getById(user.getId()));
        return alreadySaved;
    }

    @Override
    public void update(User userToUpdate, User updater) {
        ValidationUtil.validateOwnerUpdate(userToUpdate.getId(), updater.getId());
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
        ValidationUtil.validateOwnerDelete(id, user.getId());
        userRepository.delete(id);
    }

    private void validateRoleUpdate(String role, User updater) {
        if (ValidationUtil.isNotEmpty(updater.getRoles()) && !updater.getRoles().contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_ROLE_UPDATE);
        }
        if (Arrays.stream(Role.values()).noneMatch(value -> value.toString().equals(role))) {
            throw new InvalidDataException(User.class.getSimpleName(), "role", role);
        }
    }

}
