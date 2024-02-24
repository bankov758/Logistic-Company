package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.company.CompanyCreateDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyOutDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyPeriodDto;
import com.nbu.logisticcompany.entities.dtos.company.CompanyUpdateDto;
import com.nbu.logisticcompany.entities.dtos.user.CompanyEmployeesDto;
import com.nbu.logisticcompany.entities.dtos.user.UserOutDto;
import com.nbu.logisticcompany.mappers.CompanyMapper;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public CompanyController(CompanyService companyService, AuthenticationHelper authenticationHelper, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<CompanyOutDto> getAll(@RequestHeader HttpHeaders headers,
                                      @RequestParam(required = false) Optional<String> search) {
        authenticationHelper.tryGetUser(headers);
        return companyService.getAll(search).stream()
                .map(companyMapper::ObjectToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return companyMapper.ObjectToDto(companyService.getById(id));
    }

    @GetMapping("/income")
    public List<CompanyOutDto> getByIncome(@RequestHeader HttpHeaders headers,
                                           @Valid @RequestBody CompanyPeriodDto CompanyPeriodDto) {
        authenticationHelper.tryGetUser(headers);
        return companyService.getCompanyIncome(CompanyPeriodDto.getCompanyId(),
                CompanyPeriodDto.getPeriodStart(), CompanyPeriodDto.getPeriodEnd());
    }

    @GetMapping("/{id}/employees")
    public List<CompanyEmployeesDto> getCompanyEmployees(@RequestHeader HttpHeaders headers,
                                                         @PathVariable int id) {
        User creator = authenticationHelper.tryGetUser(headers);
        return companyService.getCompanyEmployees(id, creator);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers,
                                    @Valid @RequestBody CompanyCreateDto companyCreateDTO) {
        try {
            User creator = authenticationHelper.tryGetUser(headers);
            Company company = companyMapper.DtoToObject(companyCreateDTO);
            companyService.create(company, creator);
            return ResponseEntity.ok().body(companyCreateDTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                          @Valid @RequestBody CompanyUpdateDto companyUpdateDTO) {
        User updater = authenticationHelper.tryGetUser(headers);
        Company company = companyMapper.UpdateDtoToToCompany(companyUpdateDTO);
        companyService.update(company, updater);
        return ResponseEntity.ok().body(companyUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        companyService.delete(id, user);
    }

}
