package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
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
//            companies = session.createNativeQuery(
//                            " SELECT shipment.company_id AS id, company.name AS name, SUM(coalesce(price, 0)) AS income " +
//                                    " FROM shipment " +
//                                    " LEFT JOIN company ON shipment.company_id = company.id " +
//                                    " WHERE shipment.received_date > :periodStart " +
//                                    " AND shipment.received_date < :periodEnd " +
//                                    " AND shipment.company_id = :companyId " +
//                                    " GROUP BY company.id, company.name", CompanyOutDto.RESULT_SET_MAPPING_NAME)
//                    .setParameter("periodStart", periodStart)
//                    .setParameter("periodEnd", periodEnd)
//                    .setParameter("companyId", companyId)
//                    .unwrap(org.hibernate.query.NativeQuery.class)
//                    .addScalar("id", IntegerType.INSTANCE)
//                    .addScalar("name", StringType.INSTANCE)
//                    .addScalar("income", DoubleType.INSTANCE)
//                    .setResultTransformer(Transformers.aliasToBean(CompanyOutDto.class))
//                    .getResultList();
        }
    }

}
