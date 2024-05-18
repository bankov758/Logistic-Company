package com.nbu.logisticcompany.entities.dtos.company;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CompanyUpdateDto {

    @Size(min = 2, max = 40, message = "Company name should be between 2 and 40 symbols")
    private String name;
    @Positive(message = "Company IDs are positive integers")
    private  int id;

    public CompanyUpdateDto() {
    }

    public CompanyUpdateDto(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
