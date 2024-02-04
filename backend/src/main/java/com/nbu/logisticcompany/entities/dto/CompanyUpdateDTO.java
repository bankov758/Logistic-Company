package com.nbu.logisticcompany.entities.dto;

import javax.validation.constraints.NotEmpty;

public class CompanyUpdateDTO {

    @NotEmpty(message = "Name should not be empty")
    private String name;

    public CompanyUpdateDTO() {
    }

    public CompanyUpdateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
