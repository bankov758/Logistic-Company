package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Shipment;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.ShipmentCreateDto;
import com.nbu.logisticcompany.entities.dtos.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dtos.ShipmentUpdateDto;
import com.nbu.logisticcompany.mappers.ShipmentMapper;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "http://localhost:3000")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final AuthenticationHelper authenticationHelper;
    private final ShipmentMapper shipmentMapper;

    public ShipmentController(ShipmentService shipmentService, AuthenticationHelper authenticationHelper,
                              ShipmentMapper shipmentMapper) {
        this.shipmentService = shipmentService;
        this.authenticationHelper = authenticationHelper;
        this.shipmentMapper = shipmentMapper;
    }

    @GetMapping
    public List<ShipmentOutDto> getAll(@RequestHeader HttpHeaders headers,
                                       @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(headers);
        return shipmentService.getAll().stream()
                .map(shipmentMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ShipmentOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return shipmentMapper.ObjectToDto(shipmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody ShipmentCreateDto shipmentCreateDto, BindingResult result) {
        try {
            ValidationUtil.validate(result);
            User creator = authenticationHelper.tryGetUser(headers);
            Shipment shipment = shipmentMapper.createDtoToObject(shipmentCreateDto);
            shipmentService.create(shipment, creator);
            return ResponseEntity.ok().body(shipmentCreateDto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody ShipmentUpdateDto shipmentUpdateDto, BindingResult result) {
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(headers);
        Shipment shipment = shipmentMapper.updateDtoToObject(shipmentUpdateDto);
        shipmentService.update(shipment, updater);
        return ResponseEntity.ok().body(shipmentUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        shipmentService.delete(id, user);
    }

}
