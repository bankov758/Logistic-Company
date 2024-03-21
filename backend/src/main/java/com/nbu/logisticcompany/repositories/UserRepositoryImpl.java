package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
/**
 * Implementation of {@link UserRepository} using Hibernate for data access.
 */
@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {


    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    /**
     * Retrieves the company associated with a given employee ID.
     *
     * @param employeeId The ID of the employee whose company is to be retrieved.
     * @return The company associated with the given employee.
     * @throws EntityNotFoundException if no employee with the given ID exists.
     */
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
