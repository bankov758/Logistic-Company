package com.nbu.logisticcompany.entities.dtos.company;

import javax.validation.constraints.Size;

public class CompanyCreateDto {

    @Size(min = 2, max = 40, message = "Company name should be between 2 and 40 symbols")
    private String name;

    public CompanyCreateDto() {
    }

    public CompanyCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
