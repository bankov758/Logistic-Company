package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyCreateDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyPeriodDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyUpdateDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeOutDto;
import com.nbu.logisticcompany.entities.dtos.shipment.ShipmentOutDto;
import com.nbu.logisticcompany.entities.dtos.user.ClientOutDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.mappers.CompanyMapper;
import com.nbu.logisticcompany.mappers.OfficeMapper;
import com.nbu.logisticcompany.mappers.ShipmentMapper;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import com.nbu.logisticcompany.services.interfaces.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    private final CompanyService companyService;
    private final AuthenticationHelper authenticationHelper;
    private final CompanyMapper companyMapper;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentService shipmentService;
    private final OfficeService officeService;
    private final OfficeMapper officeMapper;

    public CompanyController(CompanyService companyService, AuthenticationHelper authenticationHelper,
                             CompanyMapper companyMapper, ShipmentMapper shipmentMapper,
                             ShipmentService shipmentService, OfficeService officeService, OfficeMapper officeMapper) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.companyMapper = companyMapper;
        this.shipmentMapper = shipmentMapper;
        this.shipmentService = shipmentService;
        this.officeService = officeService;
        this.officeMapper = officeMapper;
    }

    @GetMapping
    public List<CompanyOutDto> getAll(HttpSession session,
                                      @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(session);
        return companyService.getAll(search).stream()
                .map(companyMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyOutDto getById(@PathVariable int id, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return companyMapper.ObjectToDto(companyService.getById(id));
    }

    @GetMapping("/income")
    public List<CompanyOutDto> getByIncome(HttpSession session, @Valid @RequestBody CompanyPeriodDto CompanyPeriodDto) {
        authenticationHelper.tryGetUser(session);
        return companyService.getCompanyIncome(CompanyPeriodDto.getCompanyId(),
                CompanyPeriodDto.getPeriodStart(), CompanyPeriodDto.getPeriodEnd());
    }

    @GetMapping("/{id}/employees")
    public List<CompanyEmployeesDto> getCompanyEmployees(HttpSession session, @PathVariable int id) {
        User creator = authenticationHelper.tryGetUser(session);
        return companyService.getCompanyEmployees(id, creator);
    }

    @GetMapping("/{id}/clients")
    public List<ClientOutDto> getCompanyClients(HttpSession session, @PathVariable int id) {
        User creator = authenticationHelper.tryGetUser(session);
        return companyService.getCompanyClients(id, creator);
    }

    @GetMapping("/{id}/not-delivered")
    public List<ShipmentOutDto> getNotDelivered(HttpSession session, @PathVariable int id) {
        authenticationHelper.tryGetUser(session);
        return shipmentService.getNotDelivered(id).stream()
                .map(shipmentMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{companyId}/offices")
    public List<OfficeOutDto> getCompanyOffices(@PathVariable int companyId, HttpSession session) {
        authenticationHelper.tryGetUser(session);
        return officeService.filter(Optional.empty(), Optional.of(companyId), Optional.empty()).stream()
                .map(officeMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> create(HttpSession session, @Valid @RequestBody CompanyCreateDto companyCreateDTO) {
        try {
            User creator = authenticationHelper.tryGetUser(session);
            Company company = companyMapper.DtoToObject(companyCreateDTO);
            companyService.create(company, creator);
            return ResponseEntity.ok().body(companyCreateDTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(HttpSession session, @Valid @RequestBody CompanyUpdateDto companyUpdateDTO) {
        User updater = authenticationHelper.tryGetUser(session);
        Company company = companyMapper.UpdateDtoToToCompany(companyUpdateDTO);
        companyService.update(company, updater);
        return ResponseEntity.ok().body(companyUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(HttpSession session, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(session);
        companyService.delete(id, user);
    }

}
