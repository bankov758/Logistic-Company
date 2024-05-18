package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfficeEmployeeUpdateDto extends CourierUpdateDto {
    @Size(min = 2, max = 64, message = "Address should be between 2 and 64 symbols")
    @NotNull(message = "Field is mandatory")
    private String officeAddress;

    public OfficeEmployeeUpdateDto() {
    }

    public OfficeEmployeeUpdateDto(int id, String firstName, String lastName, String password,
                                   String confirmPassword, String companyName, String officeAddress) {
        super(id, firstName, lastName, password, confirmPassword, companyName);
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

}
