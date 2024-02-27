package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    @Override
    public Company getEmployeeCompany(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(" select employee.company from Employee employee" +
                                    " where employee.id =: employeeId ",
                            Company.class)
                    .setParameter("employeeId", employeeId)
                    .getSingleResult();
        }
    }

}
