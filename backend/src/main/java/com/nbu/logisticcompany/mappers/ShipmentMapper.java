package com.nbu.logisticcompany.mappers;

import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.dto.*;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ShipmentMapper {


    private final ShipmentService shipmentService;
    @Autowired
    public ShipmentMapper(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    public Shipment DTOtoObject(ShipmentCreateDto shipmentCreateDto) throws IOException {
        Shipment shipment = new Shipment();
        shipment.setId(shipmentCreateDto.getId());

        return shipment;
    }

    public ShipmentOutDto ObjectToDTO(Shipment shipment) {
        ShipmentOutDto shipmentOutDTO = new ShipmentOutDto();
        shipmentOutDTO.setId(shipmentOutDTO.getId());
        shipmentOutDTO.setArrivalAddress(shipmentOutDTO.getArrivalAddress());
        shipmentOutDTO.setDepartureAddress(shipmentOutDTO.getDepartureAddress());
        shipmentOutDTO.setEmployeeID(shipmentOutDTO.getEmployeeID());
        shipmentOutDTO.setReceiverID(shipmentOutDTO.getReceiverID());
        shipmentOutDTO.setSenderID(shipmentOutDTO.getSenderID());
        shipmentOutDTO.setReceivedFromOffice(shipmentOutDTO.isReceivedFromOffice());
        shipmentOutDTO.setSentFromOffice(shipmentOutDTO.isSentFromOffice());

        return shipmentOutDTO;
    }

    public ShipmentUpdateDto shipmentUpdateDTO(Shipment shipment) {
        ShipmentUpdateDto shipmentUpdateDTO = new ShipmentUpdateDto();

        shipmentUpdateDTO.setArrivalAddress(shipmentUpdateDTO.getArrivalAddress());
        shipmentUpdateDTO.setDepartureAddress(shipmentUpdateDTO.getDepartureAddress());
        shipmentUpdateDTO.setEmployeeID(shipmentUpdateDTO.getEmployeeID());
        shipmentUpdateDTO.setReceiverID(shipmentUpdateDTO.getReceiverID());
        shipmentUpdateDTO.setSenderID(shipmentUpdateDTO.getSenderID());
        shipmentUpdateDTO.setReceivedFromOffice(shipmentUpdateDTO.isReceivedFromOffice());
        shipmentUpdateDTO.setSentFromOffice(shipmentUpdateDTO.isSentFromOffice());

        return shipmentUpdateDTO;
    }
    // Tova otdolu izglejda greshno
    public Shipment UpdateDTOtoShipment(ShipmentUpdateDto shipmentUpdateDTO) {
        Shipment shipment = shipmentService.getById(shipmentUpdateDTO.getId());
/*  opravi tova isEmpty na ekvivalenta mu za IDta
        if (!shipmentUpdateDTO.getId().isEmpty()) {
            shipment.setId(shipmentUpdateDTO.getId());
        }
        */

        return shipment;
    }
}
