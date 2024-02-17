package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.dtos.CompanyOutDto;
import com.nbu.logisticcompany.repositories.interfaces.CompanyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CompanyRepositoryImpl extends AbstractRepository<Company> implements CompanyRepository {

    public CompanyRepositoryImpl(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }

    public List<CompanyOutDto> getCompanyIncome(int companyId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        List companies;
        try (Session session = sessionFactory.openSession()) {
            companies = session.createNativeQuery(
                            "SELECT company.id AS id, company.name AS name, SUM(coalesce(price, 0)) AS income " +
                                    "FROM company " +
                                    "LEFT JOIN employee ON company.id = employee.company_id " +
                                    "LEFT JOIN office_employee ON employee.id = office_employee.id " +
                                    "LEFT JOIN shipment ON shipment.office_employee_id = office_employee.id " +
                                    "WHERE shipment.received_date > :periodStart " +
                                    "AND shipment.received_date < :periodEnd " +
                                    "AND company.id = :companyId " +
                                    "GROUP BY company.id, company.name", CompanyOutDto.RESULT_SET_MAPPING_NAME)
                    .setParameter("periodStart", periodStart)
                    .setParameter("periodEnd", periodEnd)
                    .setParameter("companyId", companyId)
                    .unwrap(org.hibernate.query.NativeQuery.class)
                    .addScalar("id", IntegerType.INSTANCE)
                    .addScalar("name", StringType.INSTANCE)
                    .addScalar("income", DoubleType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(CompanyOutDto.class))
                    .getResultList();
        }
        return companies;
    }


}
