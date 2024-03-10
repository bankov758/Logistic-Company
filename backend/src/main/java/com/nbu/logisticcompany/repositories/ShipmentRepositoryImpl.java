package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.ShipmentStatus;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ShipmentRepositoryImpl extends AbstractRepository<Shipment> implements ShipmentRepository {

    public ShipmentRepositoryImpl(SessionFactory sessionFactory) {
        super(Shipment.class, sessionFactory);
    }

    @Override
    public Shipment getBySenderId(int senderId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment where sender.id = :senderId ", Shipment.class)
                    .setParameter("senderId", senderId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException(Shipment.class.getSimpleName(),
                            "senderId", String.valueOf(senderId)));
        }
    }

    @Override
    public Shipment getByReceiverId(int receiverId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment where receiver.id = :receiverId ", Shipment.class)
                    .setParameter("receiverId", receiverId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException(Shipment.class.getSimpleName(),
                            "receiverId", String.valueOf(receiverId)));
        }
    }

    @Override
    public Shipment getByEmployeeId(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment where employee.id = :employeeId ", Shipment.class)
                    .setParameter("employeeId", employeeId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException(Shipment.class.getSimpleName(),
                            "employeeId", String.valueOf(employeeId)));
        }
    }

    @Override
    public List<Shipment> getNotDelivered(int companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment shipment where company.id = :companyId " +
                            "  and receivedDate is null  ", Shipment.class)
                    .setParameter("companyId", companyId).getResultList();
        }
    }

    @Override
    public List<Shipment> getBySenderOrReceiver(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment where receiver.id = :userId or sender.id = :userId ", Shipment.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    @Override
    public List<Shipment> getByCompanyId(int companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment shipment where company.id = :companyId ", Shipment.class)
                    .setParameter("companyId", companyId).getResultList();
        }
    }

    @Override
    public List<Shipment> filter(Optional<Integer> senderId, Optional<Integer> receiverId,
                                 Optional<Integer> employeeId, Optional<String> shipmentStatus) {
        try (Session session = sessionFactory.openSession()) {
            var queryString = new StringBuilder(" from Shipment as shipment");
            var filter = new ArrayList<String>();
            var queryParams = new HashMap<String, Object>();

            senderId.ifPresent(value -> {
                filter.add(" sender.id = :senderId ");
                queryParams.put("senderId",  value);
            });
            receiverId.ifPresent(value -> {
                filter.add(" receiver.id = :receiverId ");
                queryParams.put("receiverId", value);
            });
            employeeId.ifPresent(value -> {
                filter.add(" employee.id = :employeeId ");
                queryParams.put("employeeId", value);
            });
            shipmentStatus.ifPresent(value -> {
                if (ShipmentStatus.ACTIVE.toString().equals(value)){
                    filter.add(" receivedDate is null ");
                }
                filter.add(" receivedDate is not null ");
            });

            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            Query<Shipment> queryList = session.createQuery(queryString.toString(), Shipment.class);
            queryList.setProperties(queryParams);
            return queryList.list();
        }
    }

}
