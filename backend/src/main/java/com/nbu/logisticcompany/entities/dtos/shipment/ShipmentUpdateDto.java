package com.nbu.logisticcompany.entities.dtos.shipment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShipmentUpdateDto extends ShipmentCreateDto {

    private int id;

    private LocalDate receivedDate;

    public ShipmentUpdateDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

}
