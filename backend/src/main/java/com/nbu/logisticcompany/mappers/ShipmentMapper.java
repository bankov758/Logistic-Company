package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentCreateDto;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentUpdateDto;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.CourierService;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import com.nbu.logisticcompany.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    private final UserService userService;
    private final OfficeEmployeeService officeEmployeeService;
    private final CourierService courierService;
    private final CompanyService companyService;

    @Autowired
    public ShipmentMapper(UserService userService, OfficeEmployeeService officeEmployeeService,
                          CourierService courierService, CompanyService companyService) {
        this.userService = userService;
        this.officeEmployeeService = officeEmployeeService;
        this.courierService = courierService;
        this.companyService = companyService;
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
        shipmentOutDto.setWeight(shipment.getWeight());
        shipmentOutDto.setSentDate(shipment.getSentDate());
        shipmentOutDto.setReceivedDate(shipment.getReceivedDate());
        shipmentOutDto.setCourierId(shipment.getCourier().getId());
        return shipmentOutDto;
    }

}
