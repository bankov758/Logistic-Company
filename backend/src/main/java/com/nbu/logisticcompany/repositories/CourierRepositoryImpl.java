package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class CourierRepositoryImpl extends AbstractRepository<Courier> implements CourierRepository {

    public CourierRepositoryImpl(SessionFactory sessionFactory) {
        super(Courier.class, sessionFactory);
    }

    @Override
    public Courier getCourierFromShipment(int shipmentId) {
        try (Session session = sessionFactory.openSession()) {
            try {
                return session.createQuery(" select shipment.courier from Shipment shipment " +
                                " WHERE shipment.id = :shipmentId  ", Courier.class)
                        .setParameter("shipmentId", shipmentId)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    @Override
    public void removeUserFromOfficeEmployees(int courierToDemoteId) {
        try (Session session = sessionFactory.openSession()) {
            session.createSQLQuery(" delete from courier where id = :id ")
                    .setParameter("id", courierToDemoteId)
                    .executeUpdate();
        }
    }

}
