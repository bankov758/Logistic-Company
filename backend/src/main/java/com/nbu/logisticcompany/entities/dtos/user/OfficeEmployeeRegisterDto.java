package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfficeEmployeeRegisterDto extends CourierRegisterDto {

    @Size(min = 2, max = 64, message = "Address should be between 2 and 64 symbols")
    @NotNull(message = "Address is mandatory")
    private String officeAddress;

    public OfficeEmployeeRegisterDto() {
    }

    public OfficeEmployeeRegisterDto(String username, String firstName, String lastName, String password,
                                     String confirmPassword, String companyName, String officeAddress) {
        super(username, firstName, lastName, password, confirmPassword, companyName);
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
