package com.nbu.logisticcompany.entities.dto;

import com.nbu.logisticcompany.entities.Company;

public class OfficeOutDto {

    private int id;
    private String address;
    private Company companyId;

    public OfficeOutDto() {
    }

    public OfficeOutDto(int id, String address, Company companyId) {
        this.id = id;
        this.address = address;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
}
