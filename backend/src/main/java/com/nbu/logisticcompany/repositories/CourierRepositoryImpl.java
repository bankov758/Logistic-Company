package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CourierRepositoryImpl extends AbstractRepository<Courier> implements CourierRepository {
    public CourierRepositoryImpl(SessionFactory sessionFactory) {
        super(Courier.class, sessionFactory);
    }
}
