package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.*;
import com.nbu.logisticcompany.mappers.CompanyMapper;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
                .map(companyMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return companyMapper.ObjectToDTO(companyService.getById(id));
    }

    @PostMapping
    public Company create(@Valid @RequestBody CompanyCreateDto companyCreateDTO) {
        try {
            Company company = companyMapper.DTOtoObject(companyCreateDTO);
            companyService.create(company);
            return company;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public Company update(@RequestHeader HttpHeaders headers,
                          @Valid @RequestBody CompanyUpdateDto companyUpdateDTO) {
        User updater = authenticationHelper.tryGetUser(headers);
        Company company = companyMapper.UpdateDTOtoToCompany(companyUpdateDTO);
        companyService.update(company, updater);
        return company;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        companyService.delete(id, user);
    }

}
