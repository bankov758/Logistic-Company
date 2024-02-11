package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeEmployeeServiceImpl implements OfficeEmployeeService {

    private final OfficeEmployeeRepository officeEmployeeRepository;

    @Autowired
    public OfficeEmployeeServiceImpl(OfficeEmployeeRepository officeEmployeeRepository) {
        this.officeEmployeeRepository = officeEmployeeRepository;
    }

    @Override
    public List<OfficeEmployee> getAll() {
        return officeEmployeeRepository.getAll();
    }

    @Override
    public OfficeEmployee getById(int id) {
        return officeEmployeeRepository.getById(id);
    }

    @Override
    public <V> OfficeEmployee getByField(String name, V value) {
        return null;
    }

    @Override
    public void create(OfficeEmployee entity) {

    }

    @Override
    public void update(OfficeEmployee entity) {

    }

    @Override
    public void delete(int id) {

    }

}
