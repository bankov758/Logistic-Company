package com.nbu.logisticcompany.entities.dtos.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourierUpdateDto extends UserUpdateDto {

    @Size(min = 2, max = 40, message = "Company name should be between 2 and 40 symbols")
    @NotNull(message = "Company name is mandatory")
    private String companyName;

    public CourierUpdateDto() {
    }

    public CourierUpdateDto(int id, String firstName, String lastName, String password,
                            String confirmPassword, String companyName) {
        super(id, firstName, lastName, password, confirmPassword);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
