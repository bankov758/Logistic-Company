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
import com.nbu.logisticcompany.services.interfaces.UserService;
import com.nbu.logisticcompany.utils.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/office-employees")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OfficeEmployeeController {

    private final OfficeEmployeeService officeEmployeeService;
    private final AuthenticationHelper authenticationHelper;
    private final OfficeEmployeeMapper officeEmployeeMapper;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentService shipmentService;
    private final UserService userService;

    public OfficeEmployeeController(OfficeEmployeeService officeEmployeeService,
                                    AuthenticationHelper authenticationHelper,
                                    OfficeEmployeeMapper officeEmployeeMapper,
                                    ShipmentMapper shipmentMapper,
                                    ShipmentService shipmentService, UserService userService) {
        this.officeEmployeeService = officeEmployeeService;
        this.authenticationHelper = authenticationHelper;
        this.officeEmployeeMapper = officeEmployeeMapper;
        this.shipmentMapper = shipmentMapper;
        this.shipmentService = shipmentService;
        this.userService = userService;
    }

    @GetMapping
    public List<OfficeEmployeeOutDto> getAll(HttpSession session,
                                             @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(session);
        return officeEmployeeService.getAll(search).stream()
                .map(officeEmployeeMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OfficeEmployeeOutDto getById(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return officeEmployeeMapper.ObjectToDto(officeEmployeeService.getById(id));
    }

    @GetMapping("/logged-employee/company-id")
    public int getCompanyId(HttpSession session) {
        User loggedUser = authenticationHelper.tryGetUser(session);
        return userService.getEmployeeCompany(loggedUser.getId()).getId();
    }

    @GetMapping("/{id}/shipments")
    public ShipmentOutDto getByEmployeeId(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
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

    @PutMapping("/demote/{id}")
    public ResponseEntity<?> demoteToUser(HttpSession session, @PathVariable int id) {
        User updater = authenticationHelper.tryGetUser(session);
        officeEmployeeService.demoteToUser(id, updater);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/make-courier/{id}")
    public ResponseEntity<?> makeCourier(HttpSession session, @PathVariable int id) {
        User updater = authenticationHelper.tryGetUser(session);
        officeEmployeeService.makeCourier(id, updater);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> update(HttpSession session,
                                    @Valid @RequestBody OfficeEmployeeUpdateDto officeEmployeeUpdateDto,
                                    BindingResult result) {
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(session);
        OfficeEmployee officeEmployee = officeEmployeeMapper.UpdateDtoToOfficeEmployee(officeEmployeeUpdateDto);
        officeEmployeeService.update(officeEmployee, updater);
        return ResponseEntity.ok().body(officeEmployeeUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(HttpSession session, @PathVariable int id) {
        User updater = authenticationHelper.tryGetUser(session);
        officeEmployeeService.delete(id, updater);
    }

}
