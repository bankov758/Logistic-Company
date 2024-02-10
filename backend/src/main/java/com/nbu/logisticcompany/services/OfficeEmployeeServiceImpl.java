package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeEmployeeServiceImpl implements OfficeEmployeeService {
    @Override
    public void delete(int id) {

    }

    @Override
    public void create(OfficeEmployee entity) {

    }

    @Override
    public void update(OfficeEmployee entity) {

    }

    @Override
    public List<OfficeEmployee> getAll() {
        return null;
    }

    @Override
    public OfficeEmployee getById(int id) {
        return null;
    }

    @Override
    public <V> OfficeEmployee getByField(String name, V value) {
        return null;
    }
}
