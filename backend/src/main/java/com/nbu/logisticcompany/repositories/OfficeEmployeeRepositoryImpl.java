package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.OfficeEmployee;
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
    public void removeUserFromOfficeEmployees(int officeEmployeeToDemoteId) {
        try (Session session = sessionFactory.openSession()) {
            session.createSQLQuery(" delete from office_employee where id = :id ")
                    .setParameter("id", officeEmployeeToDemoteId)
                    .executeUpdate();
        }
    }

    @Override
    public void makeCourier(int officeEmployeeToUpdateId) {
        try (Session session = sessionFactory.openSession()) {
            session.createSQLQuery(" INSERT INTO courier (id) VALUES (:id); ")
                    .setParameter("id", officeEmployeeToUpdateId)
                    .executeUpdate();
        }
    }

}
