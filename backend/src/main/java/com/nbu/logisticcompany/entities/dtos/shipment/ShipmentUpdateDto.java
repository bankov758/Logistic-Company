package com.nbu.logisticcompany.entities.dtos.shipment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ShipmentUpdateDto extends ShipmentCreateDto {
    @NotNull(message = "Please enter the shipment id")
    @Positive(message = "ID has to be a positive number")
    private int id;

    @PastOrPresent(message = "Receive date has to be either today or a previous date")
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
