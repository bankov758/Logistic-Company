package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.OfficeEmployee;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dtos.user.OfficeEmployeeOutDto;
import com.nbu.logisticcompany.entities.dtos.user.OfficeEmployeeRegisterDto;
import com.nbu.logisticcompany.entities.dtos.user.OfficeEmployeeUpdateDto;
import com.nbu.logisticcompany.mappers.OfficeEmployeeMapper;
import com.nbu.logisticcompany.mappers.ShipmentMapper;
import com.nbu.logisticcompany.services.interfaces.OfficeEmployeeService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/office-employees")
public class OfficeEmployeeController {

    private final OfficeEmployeeService officeEmployeeService;
    private final AuthenticationHelper authenticationHelper;
    private final OfficeEmployeeMapper officeEmployeeMapper;
    private final ShipmentMapper shipmentMapper;


    private final ShipmentService shipmentService;

    public OfficeEmployeeController(OfficeEmployeeService officeEmployeeService,
                                    AuthenticationHelper authenticationHelper,
                                    OfficeEmployeeMapper officeEmployeeMapper, ShipmentMapper shipmentMapper, ShipmentService shipmentService) {
        this.officeEmployeeService = officeEmployeeService;
        this.authenticationHelper = authenticationHelper;
        this.officeEmployeeMapper = officeEmployeeMapper;
        this.shipmentMapper = shipmentMapper;
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<OfficeEmployeeOutDto> getAll(@RequestHeader HttpHeaders headers,
                                             @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(headers);
        return officeEmployeeService.getAll(search).stream()
                .map(officeEmployeeMapper::ObjectToDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public OfficeEmployeeOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return officeEmployeeMapper.ObjectToDto(officeEmployeeService.getById(id));
    }

    @GetMapping("/{id}/shipments")
    public ShipmentOutDto getByEmployeeId(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return shipmentMapper.ObjectToDto(shipmentService.getById(id));

    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OfficeEmployeeRegisterDto officeEmployeeRegisterDto,
                                    BindingResult result) {
        ValidationUtil.validate(result);
        OfficeEmployee officeEmployee = officeEmployeeMapper.DtoToObject(officeEmployeeRegisterDto);
        officeEmployeeService.create(officeEmployee);
        return ResponseEntity.ok().body(officeEmployeeRegisterDto);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody OfficeEmployeeUpdateDto officeEmployeeUpdateDto,
                                    BindingResult result) {
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(headers);
        OfficeEmployee officeEmployee = officeEmployeeMapper.UpdateDtoToOfficeEmployee(officeEmployeeUpdateDto);
        officeEmployeeService.update(officeEmployee, updater);
        return ResponseEntity.ok().body(officeEmployeeUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User updater = authenticationHelper.tryGetUser(headers);
        officeEmployeeService.delete(id, updater);
    }

}
