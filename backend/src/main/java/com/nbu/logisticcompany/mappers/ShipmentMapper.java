package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.*;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentCreateDto;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentUpdateDto;
import com.nbu.logisticcompany.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    private final UserService userService;
    private final OfficeEmployeeService officeEmployeeService;
    private final CourierService courierService;
    private final CompanyService companyService;
    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentMapper(UserService userService, OfficeEmployeeService officeEmployeeService,
                          CourierService courierService, CompanyService companyService,
                          ShipmentService shipmentService) {
        this.userService = userService;
        this.officeEmployeeService = officeEmployeeService;
        this.courierService = courierService;
        this.companyService = companyService;
        this.shipmentService = shipmentService;
    }

    public Shipment createDtoToObject(ShipmentCreateDto shipmentCreateDto) {
        Shipment shipment = new Shipment();
        shipment.setDepartureAddress(shipmentCreateDto.getDepartureAddress());
        shipment.setArrivalAddress(shipmentCreateDto.getArrivalAddress());
        shipment.setWeight(shipmentCreateDto.getWeight());
        User sender = userService.getById(shipmentCreateDto.getSenderId());
        User receiver = userService.getById(shipmentCreateDto.getReceiverId());
        OfficeEmployee officeEmployee = officeEmployeeService.getById(shipmentCreateDto.getEmployeeId());
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setEmployee(officeEmployee);
        shipment.setSentDate(shipmentCreateDto.getSentDate());
        shipment.setCourier(courierService.getById(shipmentCreateDto.getCourierId()));
        shipment.setCompany(companyService.getById(shipmentCreateDto.getCompanyId()));
        return shipment;
    }

    public Shipment updateDtoToObject(ShipmentUpdateDto shipmentUpdateDto) {
        Shipment shipment = createDtoToObject(shipmentUpdateDto);
        shipment.setId(shipmentUpdateDto.getId());
        shipment.setReceivedDate(shipmentUpdateDto.getReceivedDate());
        return shipment;
    }

    public ShipmentOutDto ObjectToDto(Shipment shipment) {
        Courier courier = courierService.getCourierFromShipment(shipment.getId());
        User sender = shipmentService.getSender(shipment.getId());
        User receiver = shipmentService.getReceiver(shipment.getId());
        OfficeEmployee employee = shipmentService.getEmployee(shipment.getId());

        String courierUsername = courier == null ? null : courier.getUsername();
        String senderUsername = sender == null ? null : sender.getUsername();
        String receiverUsername = receiver == null ? null : receiver.getUsername();
        String employeeUsername = employee == null ? null : employee.getUsername();

        ShipmentOutDto shipmentOutDto = new ShipmentOutDto();
        shipmentOutDto.setId(shipment.getId());
        shipmentOutDto.setArrivalAddress(shipment.getArrivalAddress());
        shipmentOutDto.setDepartureAddress(shipment.getDepartureAddress());
        shipmentOutDto.setEmployee(employeeUsername);
        shipmentOutDto.setReceiver(receiverUsername);
        shipmentOutDto.setSender(senderUsername);
        shipmentOutDto.setReceivedFromOffice(shipment.isReceivedFromOffice());
        shipmentOutDto.setSentFromOffice(shipment.isSentFromOffice());
        shipmentOutDto.setPrice(shipment.getPrice());
        shipmentOutDto.setWeight(shipment.getWeight());
        shipmentOutDto.setSentDate(shipment.getSentDate());
        shipmentOutDto.setReceivedDate(shipment.getReceivedDate());
        shipmentOutDto.setCourier(courierUsername);
        shipmentOutDto.setCompanyName(shipment.getCompany().getName());
        if (shipment.getReceivedDate() == null) {
            shipmentOutDto.setStatus(ShipmentStatus.ACTIVE.toString());
        } else {
            shipmentOutDto.setStatus(ShipmentStatus.CLOSED.toString());
        }
        return shipmentOutDto;
    }

}
