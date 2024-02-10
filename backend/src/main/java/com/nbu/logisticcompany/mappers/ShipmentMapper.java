package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.*;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    private final ShipmentService shipmentService;
    private final UserService userService;
    private final OfficeEmployeeService officeEmployeeService;

    @Autowired
    public ShipmentMapper(ShipmentService shipmentService, UserService userService,
                          OfficeEmployeeService officeEmployeeService) {
        this.shipmentService = shipmentService;
        this.userService = userService;
        this.officeEmployeeService = officeEmployeeService;
    }

    public Shipment createDtoToObject(ShipmentCreateDto shipmentCreateDto) {
        Shipment shipment = new Shipment();
        shipment.setDepartureAddress(shipment.getDepartureAddress());
        shipment.setArrivalAddress(shipment.getArrivalAddress());
        shipment.setWeight(shipmentCreateDto.getWeight());
        User sender = userService.getById(shipmentCreateDto.getSenderId());
        User receiver = userService.getById(shipmentCreateDto.getReceiverId());
        OfficeEmployee officeEmployee = officeEmployeeService.getById(shipmentCreateDto.getEmployeeId());
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setEmployee(officeEmployee);
        shipment.setSentFromOffice(shipmentCreateDto.isSentFromOffice());
        shipment.setReceivedFromOffice(shipmentCreateDto.isReceivedFromOffice());
        return shipment;
    }

    public Shipment updateDtoToObject(ShipmentUpdateDto shipmentUpdateDto) {
        Shipment shipment = createDtoToObject(shipmentUpdateDto);
        shipment.setId(shipmentUpdateDto.getId());
        return shipment;
    }

    public ShipmentOutDto ObjectToDto(Shipment shipment) {
        ShipmentOutDto shipmentOutDto = new ShipmentOutDto();
        shipmentOutDto.setId(shipment.getId());
        shipmentOutDto.setArrivalAddress(shipment.getArrivalAddress());
        shipmentOutDto.setDepartureAddress(shipment.getDepartureAddress());
        shipmentOutDto.setEmployeeID(shipment.getEmployee().getId());
        shipmentOutDto.setReceiverID(shipment.getReceiver().getId());
        shipmentOutDto.setSenderID(shipment.getSender().getId());
        shipmentOutDto.setReceivedFromOffice(shipment.isReceivedFromOffice());
        shipmentOutDto.setSentFromOffice(shipment.isSentFromOffice());
        return shipmentOutDto;
    }

}
