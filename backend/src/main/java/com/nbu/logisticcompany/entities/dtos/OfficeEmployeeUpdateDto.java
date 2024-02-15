package com.nbu.logisticcompany.entities.dtos;

public class OfficeEmployeeUpdateDto extends CourierUpdateDto {

    private String officeAddress;

    public OfficeEmployeeUpdateDto() {
    }

    public OfficeEmployeeUpdateDto(int id, String firstName, String lastName, String password,
                                   String confirmPassword, String companyName, String officeAddress) {
        super(id, firstName, lastName, password, confirmPassword, companyName);
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

}
