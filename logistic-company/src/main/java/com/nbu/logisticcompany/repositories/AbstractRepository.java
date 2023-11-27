package com.nbu.logisticcompany.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AbstractRepository<T> extends AbstractReadRepository<T> {

    protected final SessionFactory sessionFactory;

    public AbstractRepository(Class<T> clazz, SessionFactory sessionFactory) {
        super(clazz, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public void create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        T toDelete = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(toDelete);
            session.getTransaction().commit();
        }
    }

}
