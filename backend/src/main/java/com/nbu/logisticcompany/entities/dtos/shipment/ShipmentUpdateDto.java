package com.nbu.logisticcompany.entities.dtos.shipment;

import java.time.LocalDateTime;

public class ShipmentUpdateDto extends ShipmentCreateDto {

    private int id;

    private LocalDateTime receivedDate;

    public ShipmentUpdateDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

}
