package com.nbu.logisticcompany.entities.dtos;

import com.nbu.logisticcompany.entities.Role;

import java.util.Set;

public class CourierOutDto extends UserOutDto {

    private String companyName;

    public CourierOutDto() {
    }

    public CourierOutDto(int id, String username, String firstName, String lastName, String companyName, Set<Role> roles) {
        super(id, username, firstName, lastName, roles);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
