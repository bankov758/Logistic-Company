package com.nbu.logisticcompany.entities.dtos.office;

import com.nbu.logisticcompany.entities.Company;

public class OfficeCreateDto {

    private String address;
    private Company companyId;

    public OfficeCreateDto() {
    }

    public OfficeCreateDto(String address, Company companyId) {
        this.address = address;
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyID(Company companyId) {
        this.companyId = companyId;
    }
}
