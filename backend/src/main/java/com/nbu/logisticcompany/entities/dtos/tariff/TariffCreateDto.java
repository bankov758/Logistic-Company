package com.nbu.logisticcompany.entities.dtos.tariff;

import com.nbu.logisticcompany.entities.Company;

public class TariffCreateDto {



    private float pricePerKG;

    private float officeDiscount;

    private Company companyID;

    public TariffCreateDto() {
    }

    public TariffCreateDto(float pricePerKG, float officeDiscount, Company companyID) {
        this.pricePerKG = pricePerKG;
        this.officeDiscount = officeDiscount;
        this.companyID = companyID;
    }

    public float getPricePerKG() {
        return pricePerKG;
    }

    public void setPricePerKG(float pricePerKG) {
        this.pricePerKG = pricePerKG;
    }

    public float getOfficeDiscount() {
        return officeDiscount;
    }

    public void setOfficeDiscount(float officeDiscount) {
        this.officeDiscount = officeDiscount;
    }

    public Company getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Company companyID) {
        this.companyID = companyID;
    }
}
