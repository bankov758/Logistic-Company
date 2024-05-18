package com.nbu.logisticcompany.entities.dtos.tariff;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;

public class TariffCreateDto {


    @Positive(message = "The price per kilogram has to be a positive number")
    private float pricePerKG;
    @Positive(message = "The discount percentage has to be a postive number")
    @Range(min = 0, max = (99/100) , message = "Discount has to be 99% at most (there's no such thing as a free lunch) ")
    private float officeDiscount;
    @Positive(message = "ID has to be a positive number")
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
