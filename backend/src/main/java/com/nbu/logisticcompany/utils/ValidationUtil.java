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

/**
 * Utility class for validation-related operations.
 */
@Component
public class ValidationUtil {

    private static final String UNAUTHORIZED_DEFAULT = "Unauthorized action";
    private static final String UNAUTHORIZED_USER_UPDATE = "Only the owner of the account can make changes";
    private static final String UNAUTHORIZED_USER_DELETE = "Only the owner of the account or an admin can delete it";
    private static final String UNAUTHORIZED_ADMIN_ACTION = "Only Admins can %s %s entities";
    private static final String UNAUTHORIZED_OFFICE_EMPLOYEE_ACTION =
            "Only office employees from the same company as the %s can modify it";
    private static final String UNAUTHORIZED_COURIER_SHIPMENT =
            "Couriers can not create or edit shipments";

    private final CourierService courierService;
    private final UserRepository userRepository;

    @Autowired
    public ValidationUtil(CourierService courierService, UserRepository userRepository) {
        this.courierService = courierService;
        this.userRepository = userRepository;
    }

    /**
     * Validates the given BindingResult. If there are validation errors, throws a ValidationException.
     *
     * @param result the BindingResult to validate
     * @throws ValidationException if there are validation errors
     */
    public static void validate(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, List<String>> errors = result.getFieldErrors().stream()
                    .collect(Collectors.groupingBy(FieldError::getField,
                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
            throw new ValidationException(errors);
        }
    }

    /**
     * Validates if the updater is the owner of the account being updated.
     *
     * @param userToUpdateId the ID of the user to update
     * @param updaterId      the ID of the updater
     * @throws UnauthorizedOperationException if the updater is not the owner
     */
    public static void validateOwnerUpdate(int userToUpdateId, int updaterId) {
        if (userToUpdateId != updaterId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_USER_UPDATE);
        }
    }

    /**
     * Validates if the destroyer is authorized to delete the user account.
     *
     * @param userToDeleteId the ID of the user to delete
     * @param destroyer      the user attempting to delete the account
     * @throws UnauthorizedOperationException if the destroyer is not authorized
     */
    public static void validateOwnerDelete(int userToDeleteId, User destroyer) {
        Set<Role> userRoles = destroyer == null || destroyer.getRoles() == null ? new HashSet<>() : destroyer.getRoles();
        int destroyerId = destroyer == null ? -1 : destroyer.getId();
        if (userToDeleteId != destroyerId && !userRoles.contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_USER_DELETE);
        }
    }

    /**
     * Validates if the user is an admin and is authorized to perform the specified action on the entity.
     *
     * @param user        the user to validate
     * @param entityClass the class of the entity
     * @param action      the action to perform
     * @param <T>         the type of the entity
     * @throws UnauthorizedOperationException if the user is not an admin or is otherwise unauthorized
     */
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

    /**
     * Authorizes an office employee to perform an action on an entity, based on their company.
     *
     * @param entityCompanyId the ID of the entity's company
     * @param user            the user to authorize
     * @param entityClass     the class of the entity
     * @param <T>             the type of the entity
     * @throws UnauthorizedOperationException if the user is not authorized
     */
    public <T> void authorizeOfficeEmployeeAction(int entityCompanyId, User user, Class<T> entityClass) {
        if (isCourier(user)){
            throw new UnauthorizedOperationException(UNAUTHORIZED_COURIER_SHIPMENT);
        }
        Set<Role> userRoles = user == null || user.getRoles() == null ? Collections.emptySet() : user.getRoles();
        if (!userRoles.contains(Role.EMPLOYEE)
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
