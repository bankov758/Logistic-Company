package com.nbu.logisticcompany.services;

import com.nbu.logisticcompany.entities.*;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.InvalidDataException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.repositories.interfaces.ShipmentRepository;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import com.nbu.logisticcompany.utils.DataUtil;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    public static final String COURIER_COMPANY_DOES_NOT_MATCH = "Shipment and courier companies do not match";
    public static final String OFFICE_EMPLOYEE_COMPANY_DOES_NOT_MATCH = "Shipment and office employee companies do not match";
    private static final int DEFAULT_PRICE_PER_KG = 1;

    private final ShipmentRepository shipmentRepository;
    private final TariffsService tariffsService;
    private final ValidationUtil validationUtil;
    private final OfficeService officeService;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, TariffsService tariffsService,
                               ValidationUtil validationUtil, OfficeService officeService) {
        this.shipmentRepository = shipmentRepository;
        this.tariffsService = tariffsService;
        this.validationUtil = validationUtil;
        this.officeService = officeService;
    }

    @Override
    public Shipment getById(int id) {
        return shipmentRepository.getById(id);
    }

    @Override
    public List<Shipment> getNotDelivered(int companyId) {
        return shipmentRepository.getNotDelivered(companyId);
    }

    @Override
    public List<Shipment> getBySenderOrReceiver(int userId) {
        return shipmentRepository.getBySenderOrReceiver(userId);
    }

    @Override
    public List<Shipment> getByCompanyId(int companyId) {
        return shipmentRepository.getByCompanyId(companyId);
    }

    @Override
    public List<Shipment> getAll() {
        return shipmentRepository.getAll();
    }

    @Override
    public List<Shipment> filter(Optional<Integer> senderId, Optional<Integer> receiverId,
                                 Optional<Integer> employeeId, Optional<String> shipmentStatus) {
        return shipmentRepository.filter(senderId, receiverId, employeeId, shipmentStatus);
    }

    @Override
    public User getSender(int shipmentId) {
        return shipmentRepository.getSender(shipmentId);
    }

    @Override
    public User getReceiver(int shipmentId) {
        return shipmentRepository.getReceiver(shipmentId);
    }

    @Override
    public OfficeEmployee getEmployee(int shipmentId) {
        return shipmentRepository.getEmployee(shipmentId);
    }

    @Override
    public void create(Shipment shipment, User creator) {
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), creator, Shipment.class);
        validateCompany(shipment);
        populateOfficeAddresses(shipment);
        applyTariff(shipment);
        shipmentRepository.create(shipment);
    }

    /**
     * Updates a shipment with new details after authorization and validations.
     *
     * @param shipmentToUpdate The shipment entity with updated details.
     * @param updater          The user performing the update operation.
     * @throws UnauthorizedOperationException If the updater is not authorized.
     */
    @Override
    public void update(Shipment shipmentToUpdate, User updater) {
        validationUtil.authorizeOfficeEmployeeAction(shipmentToUpdate.getCompany().getId(), updater, Shipment.class);
        validateCompany(shipmentToUpdate);
        populateOfficeAddresses(shipmentToUpdate);
        applyTariff(shipmentToUpdate);
        shipmentRepository.update(shipmentToUpdate);
    }

    /**
     * Deletes a shipment by its ID after ensuring the user has the necessary authorization.
     *
     * @param shipmentId The ID of the shipment to be deleted.
     * @param destroyer  The user attempting the deletion.
     * @throws EntityNotFoundException        If no shipment is found with the given ID.
     * @throws UnauthorizedOperationException If the destroyer lacks authorization.
     */
    @Override
    public void delete(int shipmentId, User destroyer) {
        Shipment shipment = shipmentRepository.getById(shipmentId);
        validationUtil.authorizeOfficeEmployeeAction(shipment.getCompany().getId(), destroyer, Shipment.class);
        shipmentRepository.delete(shipmentId);
    }

    /**
     * Validates that the shipment, courier, and office employee belong to the same company.
     *
     * @param shipment The shipment to validate.
     * @throws InvalidDataException If there's a mismatch in company IDs among the shipment, courier, or office employee.
     */
    protected void validateCompany(Shipment shipment) {
        int shipmentCompanyId = shipment.getCompany().getId();
        Courier courier = shipment.getCourier();
        int officeEmployeeCompanyId = shipment.getEmployee().getCompany().getId();
        if (shipmentCompanyId != courier.getCompany().getId()) {
            throw new InvalidDataException(COURIER_COMPANY_DOES_NOT_MATCH);
        }
        if (shipmentCompanyId != officeEmployeeCompanyId) {
            throw new InvalidDataException(OFFICE_EMPLOYEE_COMPANY_DOES_NOT_MATCH);
        }
    }

    /**
     * Calculates and applies the tariff to a shipment based on its weight and office pick-up/delivery status.
     * If the shipment is either sent or received from an office it gives 10% discount, if both -> 20%
     * It also applies price per KG based on the given tariff
     *
     * @param shipment The shipment to apply the tariff to.
     */
    protected void applyTariff(Shipment shipment) {
        Tariff tariff = tariffsService.getByCompany(shipment.getCompany().getId());
        double shipmentPrice = shipment.getWeight() * DEFAULT_PRICE_PER_KG;
        if (tariff != null) {
            double discountMultiplier = 1;
            if (shipment.isSentFromOffice() && shipment.isReceivedFromOffice()) {
                discountMultiplier = DataUtil.getPrecision2Double(2 * (tariff.getOfficeDiscount() / 100));
            } else if (shipment.isSentFromOffice() || shipment.isReceivedFromOffice()) {
                discountMultiplier = DataUtil.getPrecision2Double(tariff.getOfficeDiscount() / 100);
            }
            float pricePerKg = tariff.getPricePerKG() == 0 ? DEFAULT_PRICE_PER_KG : tariff.getPricePerKG();
            shipmentPrice = shipment.getWeight() * pricePerKg - shipment.getWeight() * pricePerKg * discountMultiplier;
        }
        shipment.setPrice(shipmentPrice);
    }

    /**
     * Determines if the shipment's departure and arrival addresses
     * match any of the company's office addresses.
     * Sets flags on the shipment accordingly.
     *
     * @param shipment The shipment to check and update.
     */
    protected void populateOfficeAddresses(Shipment shipment) {
        Company company = shipment.getCompany();
        if (shipment.getDepartureAddress() != null) {
            List<Office> offices = officeService.filter(Optional.of(shipment.getDepartureAddress()),
                    Optional.of(company.getId()), Optional.empty());
            if (DataUtil.isNotEmpty(offices)) {
                shipment.setSentFromOffice(true);
            }
        }
        if (shipment.getArrivalAddress() != null) {
            List<Office> offices = officeService.filter(Optional.of(shipment.getArrivalAddress()),
                    Optional.of(company.getId()), Optional.empty());
            if (DataUtil.isNotEmpty(offices)) {
                shipment.setReceivedFromOffice(true);
            }
        }
    }

}
