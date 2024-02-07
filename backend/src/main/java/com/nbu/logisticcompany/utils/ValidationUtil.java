package com.nbu.logisticcompany.utils;

import com.nbu.logisticcompany.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {

    public static void validate(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, List<String>> errors = result.getFieldErrors().stream()
                    .collect(Collectors.groupingBy(FieldError::getField,
                            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

            throw new ValidationException(errors);
        }
    }

}
