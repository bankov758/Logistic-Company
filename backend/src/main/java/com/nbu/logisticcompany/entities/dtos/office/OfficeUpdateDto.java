package com.nbu.logisticcompany.entities.dtos.office;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class OfficeUpdateDto {

    @Positive(message = "Company IDs are positive integers")
    @NotNull(message = "Please choose a company that the office will belong to")
    private int id;

    @Size(min = 2, max = 40, message = "Office name should be between 2 and 40 symbols")
    private String address;

    public OfficeUpdateDto() {
    }

    public OfficeUpdateDto(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
