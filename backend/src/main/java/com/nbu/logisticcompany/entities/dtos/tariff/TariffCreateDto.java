package com.nbu.logisticcompany.entities.dtos.tariff;

import com.nbu.logisticcompany.entities.Company;

public class TariffCreateDto {



    private float pricePerKG;

    private float officeDiscount;

    private int companyID;

    public TariffCreateDto() {
    }

    public TariffCreateDto(float pricePerKG, float officeDiscount, int companyID) {
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

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int     companyID) {
        this.companyID = companyID;
    }
}
