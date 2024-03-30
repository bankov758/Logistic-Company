package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Courier;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Repository
public class CourierRepositoryImpl extends AbstractRepository<Courier> implements CourierRepository {

    public CourierRepositoryImpl(SessionFactory sessionFactory) {
        super(Courier.class, sessionFactory);
    }

    /**
     * Retrieves the courier associated with a shipment.
     *
     * @param shipmentId ID of the shipment.
     * @return Courier associated with the shipment, or null if not found.
     */
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

    /**
     * Removes a user from the list of office employees by demoting them from being a courier.
     *
     * @param courierToDemoteId ID of the courier to demote.
     */
    @Override
    public void removeUserFromCouriers(int courierToDemoteId) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(" delete from courier where id = :id ")
                    .setParameter("id", courierToDemoteId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void makeOfficeEmployee(int courierId, int officeId) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(" INSERT INTO office_employee (id, office_id) VALUES (:id, :officeId); ")
                .setParameter("id", courierId)
                .setParameter("officeId", officeId)
                .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean isAlreadyCourier(int userId) {
        try {
            getById(userId);
            return true;
        } catch (EntityNotFoundException ex) {
            return false;
        }
    }

}
