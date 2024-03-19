package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.ShipmentStatus;
import com.nbu.logisticcompany.entities.User;
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

    /**
     * Retrieves the shipment associated with a specific sender's ID.
     *
     * @param senderId ID of the sender.
     * @return Shipment associated with the sender ID.
     * @throws EntityNotFoundException if the shipment associated with the sender ID is not found.
     */
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
    /**
     * Retrieves the shipment associated with a specific receiver's ID.
     *
     * @param receiverId ID of the sender.
     * @return Shipment associated with the sender ID.
     * @throws EntityNotFoundException if the shipment associated with the sender ID is not found.
     */
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

    /**
     * Retrieves the shipment associated with a specific employee's ID.
     *
     * @param employeeId ID of the sender.
     * @return Shipment associated with the sender ID.
     * @throws EntityNotFoundException if the shipment associated with the sender ID is not found.
     */
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
    /**
     * Retrieves a list of shipments that have not been delivered for a specific company.
     *
     * @param companyId ID of the company.
     * @return List of shipments that have not been delivered for the specified company.
     */
    @Override
    public List<Shipment> getNotDelivered(int companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment shipment where company.id = :companyId " +
                            "  and receivedDate is null  ", Shipment.class)
                    .setParameter("companyId", companyId).getResultList();
        }
    }

    /**
     * Retrieves a list of shipments associated with a specific sender or receiver.
     *
     * @param userId ID of the sender or receiver.
     * @return List of shipments
     */
    @Override
    public List<Shipment> getBySenderOrReceiver(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment where receiver.id = :userId or sender.id = :userId ", Shipment.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
    /**
     * Retrieves a list of shipments associated with a specific company.
     *
     * @param companyId ID of the company.
     * @return List of shipments associated with the specified company.
     */
    @Override
    public List<Shipment> getByCompanyId(int companyId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(" from Shipment shipment where company.id = :companyId ", Shipment.class)
                    .setParameter("companyId", companyId).getResultList();
        }
    }

    /**
     * Filters shipments based on optional parameters such as sender ID, receiver ID, employee ID, and shipment status.
     *
     * @param senderId      Optional sender ID to filter shipments.
     * @param receiverId    Optional receiver ID to filter shipments.
     * @param employeeId    Optional employee ID to filter shipments.
     * @param shipmentStatus Optional shipment status to filter shipments.
     * @return List of shipments filtered based on the provided parameters.
     */
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
    /**
     * Retrieves the sender of a shipment based on the shipment ID.
     *
     * @param shipmentId ID of the shipment.
     * @return The sender of the shipment.
     */
    @Override
    public User getSender(int shipmentId) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery(
                            " SELECT shipment.sender FROM Shipment shipment " +
                                    " WHERE shipment.id = :shipmentId ", User.class)
                    .setParameter("shipmentId", shipmentId)
                    .getResultList();
            return users.isEmpty() ? null : users.get(0);
        }
    }
    /**
     * Retrieves the receiver of a shipment based on the shipment ID.
     *
     * @param shipmentId ID of the shipment.
     * @return The receiver of the shipment.
     */

    @Override
    public User getReceiver(int shipmentId) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery(
                            " SELECT shipment.receiver FROM Shipment shipment " +
                                    " WHERE shipment.id = :shipmentId ", User.class)
                    .setParameter("shipmentId", shipmentId)
                    .getResultList();
            return users.isEmpty() ? null : users.get(0);
        }
    }

    /**
     * Retrieves the office employee of a shipment based on the shipment ID.
     *
     * @param shipmentId ID of the shipment.
     * @return The office employee of the shipment.
     */

    @Override
    public OfficeEmployee getEmployee(int shipmentId) {
        try (Session session = sessionFactory.openSession()) {
            List<OfficeEmployee> employees = session.createQuery(
                            " SELECT shipment.employee FROM Shipment shipment " +
                                    " WHERE shipment.id = :shipmentId ", OfficeEmployee.class)
                    .setParameter("shipmentId", shipmentId)
                    .getResultList();
            return employees.isEmpty() ? null : employees.get(0);
        }
    }

}
