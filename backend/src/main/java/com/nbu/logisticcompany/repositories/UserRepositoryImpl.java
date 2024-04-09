package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Employee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import com.nbu.logisticcompany.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserRepository} using Hibernate for data access.
 */
@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    private final OfficeRepository officeRepository;
    private final CompanyRepository companyRepository;

    public UserRepositoryImpl(SessionFactory sessionFactory, OfficeRepository _officeRepository, CompanyRepository _companyRepository) {
        super(User.class, sessionFactory);
        officeRepository = _officeRepository;
        companyRepository = _companyRepository;
    }

    @Override
    public List<User> search(Optional<String> search) {
        try (Session session = sessionFactory.openSession()) {
            if (search.isEmpty()) {
                return getAll();
            }
            Query<User> query = session.createQuery(
                    " from User where username like :name ",
                    User.class);
            query.setParameter("name", "%" + search.get() + "%");
            return query.list();
        }
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

    @Override
    public void makeOfficeEmployee(int userId, int officeId) {
        try (Session session = sessionFactory.openSession()) {
            int companyId = officeRepository.getOfficeCompany(officeId).getId();
            makeEmployee(userId, companyId);
            session.getTransaction().begin();
            session.createSQLQuery(" INSERT INTO office_employee (id, office_id) VALUES (:id, :officeId); ")
                .setParameter("id", userId)
                .setParameter("officeId", officeId)
                .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void makeCourier(int userId, int companyId) {
        try (Session session = sessionFactory.openSession()) {
            makeEmployee(userId, companyId);
            session.getTransaction().begin();
            session.createSQLQuery(" INSERT INTO courier (id) VALUES (:id); ")
                .setParameter("id", userId)
                .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void makeEmployee(int userId, int companyId) {
        try (Session session = sessionFactory.openSession()) {
            companyRepository.getById(companyId);
            session.getTransaction().begin();
            session.createSQLQuery(" INSERT INTO employee (id, company_id) VALUES (:id, :companyId); ")
                .setParameter("id", userId)
                .setParameter("companyId", companyId)
                .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean isAlreadyEmployee(int userId){
        try (Session session = sessionFactory.openSession()) {
            try {
                Employee employee = session.createQuery(" select employee from Employee employee" +
                                                            " where employee.id =: employeeId ",
                                                        Employee.class)
                    .setParameter("employeeId",  userId)
                    .getSingleResult();
                return employee != null;
            } catch (Exception ex) {
                return false;
            }
        }
    }

}
