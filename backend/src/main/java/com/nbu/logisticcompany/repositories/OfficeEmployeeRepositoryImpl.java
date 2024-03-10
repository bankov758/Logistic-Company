package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OfficeEmployeeRepositoryImpl extends AbstractRepository<OfficeEmployee> implements OfficeEmployeeRepository {

    public OfficeEmployeeRepositoryImpl(SessionFactory sessionFactory) {
        super(OfficeEmployee.class, sessionFactory);
    }
}
