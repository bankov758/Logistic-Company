package com.nbu.logisticcompany.entities.dto;

public class CompanyOutDTO {

    private int id;
    private String name;

    public CompanyOutDTO() {
    }

    public CompanyOutDTO(int id, String name) {
        this.id = id;
        this.name = name;
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
