package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TariffsRepositoryImpl extends AbstractRepository<Tariff> implements TariffsRepository {
    public TariffsRepositoryImpl(SessionFactory sessionFactory) {
        super(Tariff.class, sessionFactory);
    }

    @Override
    public Tariff getByCompany(int companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(" select tariff from Tariff tariff where tariff.company.id =: companyId ",
                            Tariff.class)
                    .setParameter("companyId", companyId)
                    .uniqueResult();
//                    .orElseThrow(() -> new EntityNotFoundException(Tariff.class.getSimpleName(),
//                            "companyId", String.valueOf(companyId)));
        }
    }

}