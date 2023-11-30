package com.nbu.logisticcompany.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public EntityNotFoundException(String type, String attribute, String id) {
        super(String.format("%s with %s %s was not found !", type, attribute, id));
    }
}

