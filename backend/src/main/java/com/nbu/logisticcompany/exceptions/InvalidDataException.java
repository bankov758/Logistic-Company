package com.nbu.logisticcompany.exceptions;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String type, String attribute, String value) {
        super(String.format("%s: %s %s is not valid", type, attribute, value));
    }

    public InvalidDataException(String message) {
        super(message);
    }

}
