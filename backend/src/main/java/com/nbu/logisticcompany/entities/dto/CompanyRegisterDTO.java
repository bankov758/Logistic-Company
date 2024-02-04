package com.nbu.logisticcompany.entities.dto;

import javax.validation.constraints.Size;

public class CompanyRegisterDTO {

    @Size(min = 2, max = 40, message = "Company name should be between 2 and 40 symbols")
    private String name;

    public CompanyRegisterDTO() {
    }

    public CompanyRegisterDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
