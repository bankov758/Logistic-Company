package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "office_employee")
@Inheritance(strategy = InheritanceType.JOINED)
public class OfficeEmployee extends Employee {

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    public OfficeEmployee(int id, String username, String password, String firstName,
                          String lastName, Set<Role> roles, Office office, Company company) {
        super(id, username, password, firstName, lastName, roles, company);
        this.office = office;
    }

    public OfficeEmployee() {
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

}
