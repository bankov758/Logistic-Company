package com.nbu.logisticcompany.entities.dtos.tariff;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;

public class TariffCreateDto {

    @Positive(message = "The price per kilogram has to be a positive number")
    private float pricePerKg;

    @Positive(message = "The discount percentage has to be a positive number")
    @Range(min = 0, max = 99, message = "Discount has to be 99% at most (there's no such thing as a free lunch) ")
    private float officeDiscount;

    @Positive(message = "Company ID has to be a positive number")
    private int companyId;

    public TariffCreateDto() {
    }

    public TariffCreateDto(float pricePerKg, float officeDiscount, int companyId) {
        this.pricePerKg = pricePerKg;
        this.officeDiscount = officeDiscount;
        this.companyId = companyId;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
