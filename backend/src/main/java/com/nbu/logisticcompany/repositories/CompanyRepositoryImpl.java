package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyCouriersDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CompanyRepositoryImpl extends AbstractRepository<Company> implements CompanyRepository {

    public CompanyRepositoryImpl(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        deleteRolesOfCompanyEmployees(id);
    }

    private void deleteRolesOfCompanyEmployees(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createSQLQuery(" delete from user_roles " +
                            " where id in (select id from employee where company_id = :id) " +
                            " and roles in ('EMPLOYEE', 'ADMIN') ")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    /**
     * Retrieves the income of a company within a specified period.
     *
     * @param companyId   ID of the company.
     * @param periodStart Start of the period.
     * @param periodEnd   End of the period.
     * @return List of CompanyOutDto objects containing company ID, name, and total income within the specified period.
     */
    public List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(" select new com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto " +
                            " (company.id, company.name, SUM(coalesce(shipment.price, 0))) from Shipment shipment " +
                            " join shipment.company company " +
                            " WHERE shipment.receivedDate >= :periodStart " +
                            " AND shipment.receivedDate <= :periodEnd " +
                            " AND company.id = :companyId " +
                            " GROUP BY company.id, company.name ", CompanyOutDto.class)
                    .setParameter("periodStart", periodStart)
                    .setParameter("periodEnd", periodEnd)
                    .setParameter("companyId", companyId).getResultList();
        }
    }

    /**
     * Retrieves employees of a company.
     *
     * @param companyId ID of the company.
     * @param user      User initiating the request.
     * @return List of CompanyEmployeesDto objects containing employee ID, username, first name, last name,
     * and company name.
     */
    public List<CompanyEmployeesDto> getCompanyEmployees(int companyId, User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(" select new com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto " +
                            " (employee.id, employee.username, employee.firstName," +
                            " employee.lastName, employee.company.name) from Employee employee " +
                            " where employee.company.id = :companyId", CompanyEmployeesDto.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    /**
     * Retrieves clients associated with a company.
     *
     * @param companyId ID of the company.
     * @param user      User initiating the request.
     * @return List of ClientOutDto objects containing client first name and last name.
     */
    public List<ClientOutDto> getCompanyClients(int companyId, User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            " select new com.nbu.logisticcompany.entities.dtos.user.ClientOutDto(user.id, user.firstName, user.lastName) " +
                                    " from User user " +
                                    " where user.id in (select distinct shipment.sender.id from Shipment shipment " +
                                    "                   where shipment.company.id =:companyId) or " +
                                    " user.id in (select distinct shipment.receiver.id from Shipment shipment " +
                                    "             where shipment.company.id =:companyId) ",
                            ClientOutDto.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

    public List<CompanyCouriersDto> getCompanyCouriers(int companyId, User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            " select new com.nbu.logisticcompany.entities.dtos.user.CompanyCouriersDto " +
                                    "(courier.id, courier.username, courier.firstName,courier.lastName, courier.company.name) " +
                                    " from Courier courier " +
                                    " where courier.company.id = :companyId ",
                            CompanyCouriersDto.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        }
    }

}
