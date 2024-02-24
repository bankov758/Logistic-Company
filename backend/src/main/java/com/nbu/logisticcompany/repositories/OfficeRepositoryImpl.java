package com.nbu.logisticcompany.repositories;

import com.nbu.logisticcompany.entities.Office;
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

    private static final String UNSUPPORTED_SORT =
            "Sort should have at least one or maximum two params divided by _ symbol!";


    public OfficeRepositoryImpl(SessionFactory sessionFactory) {
        super(Office.class, sessionFactory);
    }

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

//            sort.ifPresent(value -> {
//                queryString.append(generateSortString(value.trim()));
//            });

            Query<Office> queryList = session.createQuery(queryString.toString(), Office.class);
            queryList.setProperties(queryParams);
            return queryList.list();
        }
    }

//    private String generateSortString(String value) {
//        var queryString = new StringBuilder(" order by ");
//        String[] params = value.split("_");
//
//        if (value.isEmpty() || params.length < 1) {
//            throw new UnsupportedOperationException(UNSUPPORTED_SORT);
//        }
//        switch (params[0]) {
//            case "id":
//                queryString.append(" comment.id ");
//                break;
//            case "title":
//                queryString.append(" comment.title ");
//                break;
//            case "authorUsername":
//                queryString.append(" comment.author.username ");
//                break;
//        }
//        if (params.length > 1 && params[1].equals("desc")) {
//            queryString.append(" desc ");
//        }
//        if (params.length > 2) {
//            throw new UnsupportedOperationException(UNSUPPORTED_SORT);
//        }
//        return queryString.toString();
//    }

}