package com.nbu.logisticcompany.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "courier")
@Inheritance(strategy = InheritanceType.JOINED)
public class Courier extends Employee {

    public Courier() {
    }

    public Courier(int id, String username, String password, String firstName,
                   String lastName, Set<Role> roles, Company company) {
        super(id, username, password, firstName, lastName, roles, company);
    }

}
