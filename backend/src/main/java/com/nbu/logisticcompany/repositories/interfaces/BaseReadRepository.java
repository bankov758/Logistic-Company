package com.nbu.logisticcompany.repositories.interfaces;

import java.util.List;

public interface BaseReadRepository<T> {

    List<T> getAll();

    T getById(int id);

    <V> T getByField(String name, V value);

}
