package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OfficeEmployeeRepositoryImpl extends AbstractRepository<OfficeEmployee> implements OfficeEmployeeRepository {

    public OfficeEmployeeRepositoryImpl(SessionFactory sessionFactory) {
        super(OfficeEmployee.class, sessionFactory);
    }

    @Override
    public Company getCompany(int officeEmployeeId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" select e.company from OfficeEmployee e " +
                            " where e.id = :officeEmployeeId ", Company.class)
                    .setParameter("officeEmployeeId", officeEmployeeId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException("The company for this office employee was not found"));
        }
    }
}
