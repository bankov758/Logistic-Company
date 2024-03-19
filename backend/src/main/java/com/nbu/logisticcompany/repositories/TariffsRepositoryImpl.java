package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
/**
 * Implementation of the {@link TariffsRepository} interface to manage tariff data in the database.
 */
@Repository
public class TariffsRepositoryImpl extends AbstractRepository<Tariff> implements TariffsRepository {
    public TariffsRepositoryImpl(SessionFactory sessionFactory) {
        super(Tariff.class, sessionFactory);
    }

    /**
     * Retrieves the tariff associated with a specific company.
     *
     * @param companyId ID of the company.
     * @return Tariff associated with the company, or null if not found.
     */
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