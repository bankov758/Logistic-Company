package com.nbu.logisticcompany.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "courier")
@Inheritance(strategy = InheritanceType.JOINED)
@OnDelete(action = OnDeleteAction.CASCADE)
public class Courier extends Employee {

    public Courier() {
    }

    public Courier(int id, String username, String password, String firstName,
                   String lastName, Set<Role> roles, Company company) {
        super(id, username, password, firstName, lastName, roles, company);
    }

}
