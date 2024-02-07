package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariffs;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class TariffsRepositoryImpl extends AbstractRepository<Tariffs> implements TariffsRepository {


    public TariffsRepositoryImpl(SessionFactory sessionFactory) {
        super(Tariffs.class, sessionFactory);
    }

}