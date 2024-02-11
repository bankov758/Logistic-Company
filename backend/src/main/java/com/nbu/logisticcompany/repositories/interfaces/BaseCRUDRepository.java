package com.nbu.logisticcompany.repositories.interfaces;

public interface BaseCRUDRepository<T> extends BaseReadRepository<T> {

    void create(T entity);

    void update(T entity);

    void delete(int id);

}
