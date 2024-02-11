package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.ShipmentCreateDto;
import com.nbu.logisticcompany.entities.dto.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dto.ShipmentUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CourierService;
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
    private final CourierService courierService;

    @Autowired
    public ShipmentMapper(ShipmentService shipmentService, UserService userService,
                          OfficeEmployeeService officeEmployeeService, CourierService courierService) {
        this.shipmentService = shipmentService;
        this.userService = userService;
        this.officeEmployeeService = officeEmployeeService;
        this.courierService = courierService;
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
        shipment.setSentFromOffice(shipmentCreateDto.isSentFromOffice());
        shipment.setReceivedFromOffice(shipmentCreateDto.isReceivedFromOffice());
        shipment.setPrice(shipmentCreateDto.getPrice());
        shipment.setSentDate(shipmentCreateDto.getSentDate());
        shipment.setCourier(courierService.getById(shipmentCreateDto.getCourierId()));
        return shipment;
    }

    public Shipment updateDtoToObject(ShipmentUpdateDto shipmentUpdateDto) {
        Shipment shipment = createDtoToObject(shipmentUpdateDto);
        shipment.setId(shipmentUpdateDto.getId());
        shipment.setReceivedDate(shipmentUpdateDto.getReceivedDate());
        return shipment;
    }

    public ShipmentOutDto ObjectToDto(Shipment shipment) {
        ShipmentOutDto shipmentOutDto = new ShipmentOutDto();
        shipmentOutDto.setId(shipment.getId());
        shipmentOutDto.setArrivalAddress(shipment.getArrivalAddress());
        shipmentOutDto.setDepartureAddress(shipment.getDepartureAddress());
        shipmentOutDto.setEmployeeId(shipment.getEmployee().getId());
        shipmentOutDto.setReceiverId(shipment.getReceiver().getId());
        shipmentOutDto.setSenderId(shipment.getSender().getId());
        shipmentOutDto.setReceivedFromOffice(shipment.isReceivedFromOffice());
        shipmentOutDto.setSentFromOffice(shipment.isSentFromOffice());
        shipmentOutDto.setPrice(shipment.getPrice());
        shipmentOutDto.setSentDate(shipment.getSentDate());
        shipmentOutDto.setReceivedDate(shipment.getReceivedDate());
        shipmentOutDto.setCourierId(shipment.getCourier().getId());
        return shipmentOutDto;
    }

}
