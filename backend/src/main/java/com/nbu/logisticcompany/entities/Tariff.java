package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "tariff")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price_per_kg")
    @Positive
    private float pricePerKG;

    @Column(name = "office_discount")
    @Positive
    private float officeDiscount;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Tariff() {
    }

    public Tariff(int id, float pricePerKG, float officeDiscount, Company company) {
        this.id = id;
        this.pricePerKG = pricePerKG;
        this.officeDiscount = officeDiscount;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company companyID) {
        this.company = companyID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Float.compare(pricePerKG, tariff.pricePerKG) == 0
                && Float.compare(officeDiscount, tariff.officeDiscount) == 0
                && Objects.equals(company, tariff.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerKG, officeDiscount, company);
    }

}
