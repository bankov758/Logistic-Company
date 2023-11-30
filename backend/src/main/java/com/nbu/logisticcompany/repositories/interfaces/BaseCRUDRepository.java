package com.nbu.logisticcompany.repositories.interfaces;

public interface BaseCRUDRepository<T> extends BaseReadRepository<T> {

    void delete(int id);

    void create(T entity);

    void update(T entity);

}
