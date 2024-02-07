package com.nbu.logisticcompany.entities.dto;

public class CourierUpdateDto extends UserUpdateDto {

    private String companyName;

    public CourierUpdateDto() {
    }

    public CourierUpdateDto(int id, String firstName, String lastName, String password,
                            String confirmPassword, String companyName) {
        super(id, firstName, lastName, password, confirmPassword);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
