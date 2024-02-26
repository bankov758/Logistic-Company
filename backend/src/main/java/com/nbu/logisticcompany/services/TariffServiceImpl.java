package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.repositories.interfaces.TariffsRepository;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffsService {

    private final TariffsRepository tariffsRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public TariffServiceImpl(TariffsRepository tariffsRepository, ValidationUtil validationUtil) {
        this.tariffsRepository = tariffsRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public Tariff getById(int id) {
        return tariffsRepository.getById(id);
    }

    @Override
    public Tariff getByCompany(int companyId) {
        return tariffsRepository.getByCompany(companyId);
    }

    @Override
    public List<Tariff> getAll() {
        return tariffsRepository.getAll();
    }

    @Override
    public void create(Tariff tariff, User creator) {
        validationUtil.authorizeOfficeEmployeeAction(tariff.getCompany().getId(), creator, Tariff.class);
        boolean duplicateTariff = true;
        try {
            tariffsRepository.getByCompany(tariff.getCompany().getId());
        } catch (EntityNotFoundException e) {
            duplicateTariff = false;
        }
        if (duplicateTariff) {
            throw new DuplicateEntityException("Tariff", "company", tariff.getCompany().getName());
        }
        tariffsRepository.create(tariff);
    }

    @Override
    public void update(Tariff tariffToUpdate, User updater) {
        validationUtil.authorizeOfficeEmployeeAction(tariffToUpdate.getCompany().getId(), updater, Tariff.class);
        tariffsRepository.update(tariffToUpdate);
    }

    @Override
    public void delete(int tariffId, User destroyer) {
        Tariff tariff = tariffsRepository.getById(tariffId);
        validationUtil.authorizeOfficeEmployeeAction(tariff.getCompany().getId(), destroyer, Tariff.class);
        tariffsRepository.delete(tariffId);
    }

}
