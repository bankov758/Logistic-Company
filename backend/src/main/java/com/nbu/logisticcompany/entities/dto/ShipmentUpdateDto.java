package com.nbu.logisticcompany.entities.dto;

public class ShipmentUpdateDto extends ShipmentCreateDto {

    private int id;

    public ShipmentUpdateDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
