package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.OfficeRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import com.nbu.logisticcompany.utils.Action;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void create(Office office, User creator) {
        ValidationUtil.validateAdminAction(creator, Office.class, Action.CREATE);
        List<Office> existingOffices = officeRepository.filter(Optional.ofNullable(office.getAddress()),
                Optional.of(office.getCompany().getId()),
                Optional.empty());
        if (ValidationUtil.isNotEmpty(existingOffices)) {
            throw new DuplicateEntityException(String.format("An office for %s already exists at address - %s",
                    office.getCompany().getName(), office.getAddress()));

        }
        officeRepository.create(office);
    }

    @Override
    public void update(Office officeToUpdate, User updater) {
        ValidationUtil.validateAdminAction(updater, Office.class, Action.UPDATE);
        officeRepository.update(officeToUpdate);
    }

    @Override
    public void delete(int officeId, User destroyer) {
        ValidationUtil.validateAdminAction(destroyer, Office.class, Action.DELETE);
        officeRepository.delete(officeId);
    }

}
