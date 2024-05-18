package com.nbu.logisticcompany.entities.dtos.user;

import com.nbu.logisticcompany.entities.Role;

import java.util.Set;

public class OfficeEmployeeOutDto extends CourierOutDto {

    private String officeAddress;

    public OfficeEmployeeOutDto() {
    }

    public OfficeEmployeeOutDto(int id, String username, String firstName, String lastName, String companyName,
                                Set<Role> roles, String officeAddress) {
        super(id, username, firstName, lastName, companyName, roles);
        this.officeAddress = officeAddress;
    }

    public OfficeEmployeeOutDto(int id, String username, String firstName, String lastName,
                                String companyName, String officeAddress) {
        super(id, username, firstName, lastName, companyName, null);
        this.officeAddress = officeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
