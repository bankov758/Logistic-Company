package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Employee() {
    }

    public Employee(int id, String username, String password, String firstName,
                    String lastName, Set<Role> roles, Company company) {
        super(id, username, password, firstName, lastName, roles);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
