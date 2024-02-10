package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public Office getById(int id) {
        return officeRepository.getById(id);
    }

    @Override
    public Office getByAddress(String address) {
        return officeRepository.getByField("address", address);
    }

    @Override
    public Office getByCompany(Company company) {
        return officeRepository.getByField("company", company);
    }

    @Override
    public List<Office> getAll() {
        return officeRepository.getAll();
    }

    @Override
    public void create(Office office) {
        boolean duplicateOffice = true;
        try {
            officeRepository.getByField("id", office.getId());
        } catch (EntityNotFoundException e) {
            duplicateOffice = false;
        }
        officeRepository.create(office);
    }

    @Override
    public void update(Office officeToUpdate, User user) {
        officeRepository.update(officeToUpdate);
    }

    @Override
    public void delete(int officeId, User user) {
        officeRepository.delete(officeId);
    }

}
