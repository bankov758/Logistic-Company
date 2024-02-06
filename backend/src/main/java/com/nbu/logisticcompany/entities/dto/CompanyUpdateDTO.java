package com.nbu.logisticcompany.entities.dto;

import javax.validation.constraints.NotEmpty;

public class CompanyUpdateDTO {


    private String name;

    private  int id;

    public CompanyUpdateDTO() {
    }

    public CompanyUpdateDTO(String name, int id) {
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
