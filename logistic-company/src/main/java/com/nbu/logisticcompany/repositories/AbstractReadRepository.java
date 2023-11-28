package com.nbu.logisticcompany.repositories;

import java.util.List;

import com.nbu.logisticcompany.repositories.interfaces.BaseReadRepository;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static java.lang.String.format;

public abstract class AbstractReadRepository<T> implements BaseReadRepository<T> {

    private final Class<T> clazz;
    protected final SessionFactory sessionFactory;

    public AbstractReadRepository(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Retrieves an entity from the database that has a <code>field</code> equal to <code>value</code>.
     * <br/>
     * Example: <code>getByField("id", 1, Parcel.class)</code>
     * will execute the following HQL: <code>from Parcel where id = 1;</code>
     *
     * @param name  the name of the field
     * @param value the value of the field
     * @return an entity that matches the given criteria
     */
    public <V> T getByField(String name, V value) {
        final String query = format(" from %s where %s = :value", clazz.getSimpleName(), name);
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery(query, clazz)
                    .setParameter("value", value)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new EntityNotFoundException(clazz.getSimpleName(), name, String.valueOf(value)));
        }
    }

    @Override
    public T getById(int id) {
        return getByField("id", id);
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String query = format(" from %s ", clazz.getName());
            return session.createQuery(query, clazz).list();
        }
    }

}
