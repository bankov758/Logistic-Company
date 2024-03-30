package com.nbu.logisticcompany.repositories;

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

    /**
     * Removes a user from the employees of an office.
     *
     * @param officeEmployeeToDemoteId ID of the office employee to demote.
     */
    @Override
    public void removeUserFromOfficeEmployees(int officeEmployeeToDemoteId) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(" delete from office_employee where id = :id ")
                    .setParameter("id", officeEmployeeToDemoteId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    /**
     * Updates the role of an office employee to courier.
     *
     * @param officeEmployeeToUpdateId ID of the office employee to update.
     */
    @Override
    public void makeCourier(int officeEmployeeToUpdateId) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(" INSERT INTO courier (id) VALUES (:id); ")
                    .setParameter("id", officeEmployeeToUpdateId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean isAlreadyOfficeEmployee(int userId) {
        try {
            getById(userId);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

}
