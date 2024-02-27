package com.nbu.logisticcompany.entities.dtos.office;

public class OfficeCreateDto {

    private String address;
    private int companyId;

    public OfficeCreateDto() {
    }

    public OfficeCreateDto(String address, int companyId) {
        this.address = address;
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyID(int companyId) {
        this.companyId = companyId;
    }
}
