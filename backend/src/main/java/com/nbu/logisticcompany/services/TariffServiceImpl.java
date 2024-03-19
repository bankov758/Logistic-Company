package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
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

    /**
     * Creates a new tariff, checking for creator's authorization and tariff uniqueness.
     * Ensures the creator is authorized and no existing tariff for the company exists before saving the new tariff.
     *
     * @param tariff The tariff to be created.
     * @param creator The user responsible for creating the tariff.
     * @throws UnauthorizedOperationException if creator lacks necessary permissions.
     * @throws DuplicateEntityException if a similar tariff already exists.
     */
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

    /**
     * Updates an existing tariff, with authorization checks for the updater.
     *
     * @param tariffToUpdate The tariff object to be updated.
     * @param updater The user attempting the update, must be verified for proper authorization.
     * @throws UnauthorizedOperationException if the updater lacks authorization per {@code ValidationUtil}.
     */
    @Override
    public void update(Tariff tariffToUpdate, User updater) {
        validationUtil.authorizeOfficeEmployeeAction(tariffToUpdate.getCompany().getId(), updater, Tariff.class);
        tariffsRepository.update(tariffToUpdate);
    }

    /**
     * Deletes a specified tariff after authorizing the operation.
     *
     * @param tariffId The ID of the tariff to delete.
     * @param destroyer The user attempting the deletion.
     * @throws EntityNotFoundException If the tariff doesn't exist.
     * @throws UnauthorizedOperationException If the user lacks deletion rights.
     */
    @Override
    public void delete(int tariffId, User destroyer) {
        Tariff tariff = tariffsRepository.getById(tariffId);
        validationUtil.authorizeOfficeEmployeeAction(tariff.getCompany().getId(), destroyer, Tariff.class);
        tariffsRepository.delete(tariffId);
    }

}
