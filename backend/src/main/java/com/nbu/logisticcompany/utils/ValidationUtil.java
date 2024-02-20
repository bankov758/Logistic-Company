package com.nbu.logisticcompany.utils;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.exceptions.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {

    private static final String UNAUTHORIZED_DEFAULT = "Unauthorized action";
    private static final String UNAUTHORIZED_USER_UPDATE = "Only the owner of the account can make changes";
    private static final String UNAUTHORIZED_USER_DELETE = "Only the owner of the account can delete it";
    private static final String UNAUTHORIZED_ADMIN_ACTION = "Only Admins can %s %s entities";

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

    public static void validateOwnerDelete(int userToDeleteId, int destroyerId) {
        if (userToDeleteId != destroyerId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_USER_DELETE);
        }
    }

    public static <T> void validateAdminAction(User user, Class<T> clsss, Action action) {
        String errorMessage;
        if (user == null || clsss == null || action == null) {
            errorMessage = UNAUTHORIZED_DEFAULT;
        } else {
            errorMessage = String.format(UNAUTHORIZED_ADMIN_ACTION,
                    action.toString().toLowerCase(), clsss.getSimpleName());
        }
        if (user != null && isNotEmpty(user.getRoles()) && !user.getRoles().contains(Role.ADMIN)) {
            throw new UnauthorizedOperationException(errorMessage);
        }
    }

}
