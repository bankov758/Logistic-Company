package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class OfficeRepositoryImpl extends AbstractRepository<Office> implements OfficeRepository {
    public OfficeRepositoryImpl(SessionFactory sessionFactory) {
        super(Office.class, sessionFactory);
    }
}