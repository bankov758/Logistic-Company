package com.nbu.logisticcompany.entities;

import javax.persistence.*;

@Entity
@Table(name = "office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
