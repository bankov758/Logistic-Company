package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl extends AbstractRepository<Company> implements CompanyRepository {
    public CompanyRepositoryImpl(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }
}
