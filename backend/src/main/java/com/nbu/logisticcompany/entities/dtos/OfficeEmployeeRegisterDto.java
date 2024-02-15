package com.nbu.logisticcompany.entities.dtos;

public class OfficeEmployeeRegisterDto extends CourierRegisterDto {

    private String officeAddress;

    public OfficeEmployeeRegisterDto() {
    }

    public OfficeEmployeeRegisterDto(String username, String firstName, String lastName, String password,
                                     String confirmPassword, String companyName, String officeAddress) {
        super(username, firstName, lastName, password, confirmPassword, companyName);
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
