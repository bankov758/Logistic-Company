package com.nbu.logisticcompany.entities;

import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@SqlResultSetMapping(name = CompanyOutDto.RESULT_SET_MAPPING_NAME,
        classes = {@ConstructorResult(targetClass = CompanyOutDto.class,
        columns = {@ColumnResult(name = "id"), @ColumnResult(name = "name"), @ColumnResult(name = "income")})})
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public Company() {
    }

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
