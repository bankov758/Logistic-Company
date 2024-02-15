package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Role;
import com.nbu.logisticcompany.repositories.interfaces.RoleRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {
    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        super(Role.class, sessionFactory);
    }
}
