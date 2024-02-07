package com.nbu.logisticcompany.entities.dto;

import com.nbu.logisticcompany.entities.Company;

import javax.validation.constraints.Positive;

public class TariffsCreateDTO {



    private float pricePerKG;

    private float officeDiscount;

    private Company companyID;

    public TariffsCreateDTO() {
    }

    public TariffsCreateDTO(float pricePerKG, float officeDiscount, Company companyID) {
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
