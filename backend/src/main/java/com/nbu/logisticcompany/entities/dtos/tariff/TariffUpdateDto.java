package com.nbu.logisticcompany.entities.dtos.tariff;

import com.nbu.logisticcompany.entities.Company;

public class TariffUpdateDto {

    private int id;

    private float pricePerKg;

    private float officeDiscount;

    private Company companyId;

    public TariffUpdateDto() {
    }

    public TariffUpdateDto(int id, float pricePerKg, float officeDiscount, Company companyId) {
        this.id = id;
        this.pricePerKg = pricePerKg;
        this.officeDiscount = officeDiscount;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(float pricePerKG) {
        this.pricePerKg = pricePerKG;
    }

    public float getOfficeDiscount() {
        return officeDiscount;
    }

    public void setOfficeDiscount(float officeDiscount) {
        this.officeDiscount = officeDiscount;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyID) {
        this.companyId = companyID;
    }

}
