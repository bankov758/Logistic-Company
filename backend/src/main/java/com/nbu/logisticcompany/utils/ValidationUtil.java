package com.nbu.logisticcompany.utils;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.exceptions.ValidationException;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ValidationUtil {

    private static final String UNAUTHORIZED_DEFAULT = "Unauthorized action";
    private static final String UNAUTHORIZED_USER_UPDATE = "Only the owner of the account can make changes";
    private static final String UNAUTHORIZED_USER_DELETE = "Only the owner of the account or an admin can delete it";
    private static final String UNAUTHORIZED_ADMIN_ACTION = "Only Admins can %s %s entities";
    private static final String UNAUTHORIZED_OFFICE_EMPLOYEE_ACTION =
            "Only office employees from the same company as the %s can modify it";

    private final CourierService courierService;
    private final UserRepository userRepository;

    @Autowired
    public ValidationUtil(CourierService courierService, UserRepository userRepository) {
        this.courierService = courierService;
        this.userRepository = userRepository;
    }

    public static void validate(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, List<String>> errors = result.getFieldErrors().stream()
                    .collect(Collectors.groupingBy(FieldError::getField,
                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
            throw new ValidationException(errors);
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static void validateOwnerUpdate(int userToUpdateId, int updaterId) {
        if (userToUpdateId != updaterId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_USER_UPDATE);
        }
    }

    public static void validateOwnerDelete(int userToDeleteId, User destroyer) {
        Set<Role> userRoles = destroyer == null || destroyer.getRoles() == null ? new HashSet<>() : destroyer.getRoles();
        int destroyerId = destroyer == null ? -1 : destroyer.getId();
        if (userToDeleteId != destroyerId && !userRoles.contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_USER_DELETE);
        }
    }

    public static <T> void validateAdminAction(User user, Class<T> entityClass, Action action) {
        String errorMessage;
        if (user == null || entityClass == null || action == null) {
            errorMessage = UNAUTHORIZED_DEFAULT;
        } else {
            errorMessage = String.format(UNAUTHORIZED_ADMIN_ACTION,
                    action.toString().toLowerCase(), entityClass.getSimpleName());
        }
        Set<Role> userRoles = user == null || user.getRoles() == null ? Collections.emptySet() : user.getRoles();
        if (!userRoles.contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(errorMessage);
        }
    }

    public <T> void authorizeOfficeEmployeeAction(int entityCompanyId, User user, Class<T> entityClass) {
        Set<Role> userRoles = user == null || user.getRoles() == null ? Collections.emptySet() : user.getRoles();
        if (!userRoles.contains(Role.EMPLOYEE)
                || isCourier(user)
                || userRepository.getEmployeeCompany(user.getId()).getId() != entityCompanyId) {
            throw new UnauthorizedOperationException(String.format(UNAUTHORIZED_OFFICE_EMPLOYEE_ACTION,
                    entityClass.getSimpleName().toLowerCase()));
        }
    }

    private boolean isCourier(User user) {
        try {
            courierService.getById(user.getId());
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }

}
