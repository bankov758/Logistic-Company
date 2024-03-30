package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class OfficeRepositoryImpl extends AbstractRepository<Office> implements OfficeRepository {

    public OfficeRepositoryImpl(SessionFactory sessionFactory) {
        super(Office.class, sessionFactory);
    }

    /**
     * Filters offices based on optional parameters such as address, company ID, and sort criteria.
     *
     * @param address   Optional address to filter offices.
     * @param companyId Optional company ID to filter offices.
     * @param sort      Optional sort criteria for offices.
     * @return List of offices filtered based on the provided parameters.
     */

    public List<Office> filter(Optional<String> address, Optional<Integer> companyId, Optional<String> sort) {
        try (Session session = sessionFactory.openSession()) {
            var queryString = new StringBuilder(" from Office as office");
            var filter = new ArrayList<String>();
            var queryParams = new HashMap<String, Object>();

            address.ifPresent(value -> {
                filter.add(" office.address = :address ");
                queryParams.put("address", value.trim());
            });

            companyId.ifPresent(value -> {
                filter.add(" office.company.id = :companyId ");
                queryParams.put("companyId", value);
            });

            if (!filter.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filter));
            }

            Query<Office> queryList = session.createQuery(queryString.toString(), Office.class);
            queryList.setProperties(queryParams);
            return queryList.list();
        }
    }

    @Override
    public Company getOfficeCompany(int officeId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    " SELECT office.company FROM Office office " +
                        " WHERE office.id = :officeId ", Company.class)
                .setParameter("officeId", officeId)
                .getSingleResult();
        }
    }

}