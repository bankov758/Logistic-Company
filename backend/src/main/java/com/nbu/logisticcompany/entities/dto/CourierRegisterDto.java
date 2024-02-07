package com.nbu.logisticcompany.entities.dto;

public class CourierRegisterDto extends UserRegisterDto {

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
