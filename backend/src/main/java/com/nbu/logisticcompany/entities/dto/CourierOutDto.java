package com.nbu.logisticcompany.entities.dto;

public class CourierOutDto extends UserOutDTO {

    private String companyName;

    public CourierOutDto() {
    }

    public CourierOutDto(int id, String username, String firstName, String lastName, String companyName) {
        super(id, username, firstName, lastName);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
