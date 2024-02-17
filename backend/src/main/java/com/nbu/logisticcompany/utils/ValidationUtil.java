package com.nbu.logisticcompany.utils;

import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.exceptions.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {

    private static final String UNAUTHORIZED_UPDATE = "Only the owner of the account can make changes";

    private static final String UNAUTHORIZED_DELETE = "Only the owner of the account can delete it";

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

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static void validateOwnerUpdate(int userToUpdateId, int updaterId){
        if (userToUpdateId != updaterId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_UPDATE);
        }
    }

    public static void validateOwnerDelete(int userToDeleteId, int updaterId){
        if (userToDeleteId != updaterId) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETE);
        }
    }

}
