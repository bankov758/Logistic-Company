package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

}
