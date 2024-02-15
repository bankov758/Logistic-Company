package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class TariffsRepositoryImpl extends AbstractRepository<Tariff> implements TariffsRepository {
    public TariffsRepositoryImpl(SessionFactory sessionFactory) {
        super(Tariff.class, sessionFactory);
    }
}