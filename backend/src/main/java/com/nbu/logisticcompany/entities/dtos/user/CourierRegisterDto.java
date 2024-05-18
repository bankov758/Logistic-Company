package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourierRegisterDto extends UserRegisterDto {
    @Size(min = 2, max = 40, message = "Company name should be between 2 and 40 symbols")
    @NotNull(message = "Field is mandatory")
    private String companyName;

    public CourierRegisterDto() {
    }

    public CourierRegisterDto(String username, String firstName, String lastName, String password,
                              String confirmPassword, String companyName) {
        super(username, firstName, lastName, password, confirmPassword);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
