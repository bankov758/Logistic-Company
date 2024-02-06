package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ShipmentRepositoryImpl extends AbstractRepository<Shipment> implements ShipmentRepository {

    public ShipmentRepositoryImpl(SessionFactory sessionFactory) {
        super(Shipment.class, sessionFactory);
    }

}
