package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.repositories.interfaces.CourierRepository;
import com.nbu.logisticcompany.repositories.interfaces.OfficeEmployeeRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import com.nbu.logisticcompany.utils.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nbu.logisticcompany.utils.ValidationUtil.*;

@Service
public class OfficeEmployeeServiceImpl implements OfficeEmployeeService {

    private final OfficeEmployeeRepository officeEmployeeRepository;
    private final CourierRepository courierRepository;

    @Autowired
    public OfficeEmployeeServiceImpl(OfficeEmployeeRepository officeEmployeeRepository, CourierRepository courierRepository) {
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.courierRepository = courierRepository;
    }

    @Override
    public List<OfficeEmployee> getAll(Optional<String> search) {
        return officeEmployeeRepository.getAll();
    }

    @Override
    public OfficeEmployee getById(int id) {
        return officeEmployeeRepository.getById(id);
    }

    @Override
    public OfficeEmployee getByUsername(String username) {
        return officeEmployeeRepository.getByField("username", username);
    }

    /**
     * Creates a new office employee, ensuring the employee's company matches the office's company.
     *
     * @param officeEmployee The office employee to be created.
     * @throws InvalidDataException If the company IDs of the office and employee do not match.
     */
    @Override
    public void create(OfficeEmployee officeEmployee) {
        boolean duplicateUser = true;
        try {
            officeEmployeeRepository.getByField("username", officeEmployee.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateUser = false;
        }
        if (duplicateUser) {
            throw new DuplicateEntityException("User", "username", officeEmployee.getUsername());
        }
        if (officeEmployee.getOffice().getCompany().getId() != officeEmployee.getCompany().getId()){
            throw new InvalidDataException("Office employee company and office company do not match");
        }
        officeEmployeeRepository.create(officeEmployee);
    }

    /**
     * Demotes an office employee to a regular user role, provided the updater has admin privileges.
     *
     * @param officeEmployeeToDemoteId The ID of the office employee to demote.
     * @param updater The admin user performing the demotion.
     * @throws UnauthorizedOperationException If the updater is not authorized as an admin.
     */
    @Override
    public void demoteToUser(int officeEmployeeToDemoteId, User updater) {
        validateAdminAction(updater, OfficeEmployee.class, Action.UPDATE);
        officeEmployeeRepository.removeUserFromOfficeEmployees(officeEmployeeToDemoteId);
    }

    /**
     * Changes an existing office employee to a courier role, ensuring the operation is performed by an authorized admin.
     *
     * @param officeEmployeeToUpdateId The ID of the office employee to be promoted to courier.
     * @param updater The admin user performing the change.
     * @throws UnauthorizedOperationException If the updater is not authorized as an admin.
     */
    @Override
    public void makeCourier(int officeEmployeeToUpdateId, User updater) {
        validateAdminAction(updater, OfficeEmployee.class, Action.UPDATE);
        if (courierRepository.isAlreadyCourier(officeEmployeeToUpdateId)){
            throw new DuplicateEntityException(OfficeEmployee.class.getSimpleName(), "id",
                                               String.valueOf(officeEmployeeToUpdateId));
        }
        officeEmployeeRepository.removeUserFromOfficeEmployees(officeEmployeeToUpdateId);
        officeEmployeeRepository.makeCourier(officeEmployeeToUpdateId);
    }

    /**
     * Updates an office employee's details after ownership and company validation.
     *
     * @param officeEmployeeToUpdate The office employee to update.
     * @param updater The user attempting the update.
     * @throws UnauthorizedOperationException If the updater is not authorized.
     * @throws InvalidDataException If company IDs mismatch.
     */
    @Override
    public void update(OfficeEmployee officeEmployeeToUpdate, User updater) {
        validateOwnerUpdate(officeEmployeeToUpdate.getId(), updater.getId());
        if (officeEmployeeToUpdate.getOffice().getCompany().getId() != officeEmployeeToUpdate.getCompany().getId()){
            throw new InvalidDataException("Office employee company and office company do not match");
        }
        officeEmployeeRepository.update(officeEmployeeToUpdate);
    }

    /**
     * Deletes an office employee by ID after validating the updater's ownership.
     *
     * @param id The ID of the office employee to delete.
     * @param updater The user attempting the deletion.
     * @throws UnauthorizedOperationException If the updater is not the owner.
     */
    @Override
    public void delete(int id, User updater) {
        validateOwnerDelete(id, updater);
        officeEmployeeRepository.delete(id);
    }

}
