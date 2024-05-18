package com.nbu.logisticcompany.entities.dtos.tariff;

import com.nbu.logisticcompany.entities.Company;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TariffUpdateDto {

    private int id;
    @Positive(message = "The price per kilogram has to be a positive number")
    private float pricePerKg;
    @Positive(message = "The discount percentage has to be a postive number")
    @Range(min = 0, max = (99/100) , message = "Discount has to be 99% at most (there's no such thing as a free lunch) ")
    private float officeDiscount;
    @Positive(message = "ID has to be a positive number")
    @NotNull(message = "Field is mandatory")
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
