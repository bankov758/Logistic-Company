package com.nbu.logisticcompany.entities.dtos;

public class CompanyOutDto {

    public static final String RESULT_SET_MAPPING_NAME = "CompanyDTOMapping";

    private int id;
    private String name;
    private double income;

    public CompanyOutDto() {
    }

    public CompanyOutDto(int id, String name) {
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
