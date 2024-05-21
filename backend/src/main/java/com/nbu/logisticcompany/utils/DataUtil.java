package com.nbu.logisticcompany.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Utility class for common data-related operations.
 */
public class DataUtil {

    /**
     * Checks if a string is empty or null.
     *
     * @param string the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks if a string is not empty and not null.
     *
     * @param string the string to check
     * @return true if the string is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * Checks if a collection is empty or null.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to check
     * @return true if the collection is null or empty, false otherwise
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if a collection is not empty and not null.
     *
     * @param <T>        the type of elements in the collection
     * @param collection the collection to check
     * @return true if the collection is not null and not empty, false otherwise
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    /**
     * Rounds a double value to two decimal places using half-up rounding mode.
     *
     * @param value the double value to round
     * @return the rounded double value
     */
    public static double getPrecision2Double(double value){
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Extracts the default messages from the binding result errors.
     *
     * @param errors the BindingResult containing field errors
     * @return a stream of default messages from the field errors
     */
    public static Stream<String> getDefaultMessages(BindingResult errors) {
        return errors.getFieldErrors().stream().map(FieldError::getDefaultMessage);
    }

}
