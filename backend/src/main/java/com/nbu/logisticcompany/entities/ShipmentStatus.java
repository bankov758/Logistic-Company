package com.nbu.logisticcompany.entities;

public enum ShipmentStatus {

    ACTIVE("Active"),
    CLOSED("Closed");

    private final String status;

    ShipmentStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
