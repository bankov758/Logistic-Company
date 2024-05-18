package com.nbu.logisticcompany.entities.dtos.office;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class OfficeCreateDto {

    @Size(min = 2, max = 40, message = "Office name should be between 2 and 40 symbols")
    private String address;

    @Positive(message = "Company IDs are positive integers")
    @NotNull(message = "Please choose a company that the office will belong to")
    private int companyId;

    public OfficeCreateDto() {
    }

    public OfficeCreateDto(String address, int companyId) {
        this.address = address;
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyID(int companyId) {
        this.companyId = companyId;
    }
}
