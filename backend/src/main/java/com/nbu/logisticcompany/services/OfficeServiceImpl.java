package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
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
    public List<Office> filter(Optional<String> address, Optional<Integer> companyId, Optional<String> sort) {
        return officeRepository.filter(address, companyId, sort);
    }

    @Override
    public List<Office> getAll() {
        return officeRepository.getAll();
    }

    /**
     * Creates a new office, ensuring no duplicate exists for the same address within the company, and the creator is authorized.
     *
     * @param office The office to be created.
     * @param creator The user attempting to create the office.
     * @throws UnauthorizedOperationException If the creator lacks admin privileges.
     * @throws DuplicateEntityException If an office at the same address already exists for the company.
     */
    @Override
    public void create(Office office, User creator) {
        ValidationUtil.validateAdminAction(creator, Office.class, Action.CREATE);
        List<Office> existingOffices = officeRepository.filter(Optional.ofNullable(office.getAddress()),
                Optional.of(office.getCompany().getId()), Optional.empty());
        if (ValidationUtil.isNotEmpty(existingOffices)) {
            throw new DuplicateEntityException(String.format("An office for %s already exists at address - %s",
                    office.getCompany().getName(), office.getAddress()));

        }
        officeRepository.create(office);
    }
    /**
     * Updates an existing office's details, given that the updater has admin privileges.
     *
     * @param officeToUpdate The office entity with updated information.
     * @param updater The user performing the update operation.
     * @throws UnauthorizedOperationException If the updater is not an admin.
     */
    @Override
    public void update(Office officeToUpdate, User updater) {
        ValidationUtil.validateAdminAction(updater, Office.class, Action.UPDATE);
        officeRepository.update(officeToUpdate);
    }
    /**
     * Deletes an office by its ID, ensuring the operation is performed by an authorized admin.
     * @param officeId The ID of the office to be deleted.
     * @param destroyer The admin user attempting the deletion.
     * @throws UnauthorizedOperationException If the destroyer is not authorized as an admin.
     */
    @Override
    public void delete(int officeId, User destroyer) {
        ValidationUtil.validateAdminAction(destroyer, Office.class, Action.DELETE);
        officeRepository.delete(officeId);
    }

}
