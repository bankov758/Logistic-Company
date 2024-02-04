package com.nbu.logisticcompany.controllers;


import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.dto.CompanyOutDTO;
import com.nbu.logisticcompany.entities.dto.UserOutDTO;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.mappers.CompanyMapper;
import com.nbu.logisticcompany.mappers.UserMapper;
import com.nbu.logisticcompany.services.interfaces.CompanyService;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private  final CompanyService companyService;

    private  final AuthenticationHelper authenticationHelper;

    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, AuthenticationHelper authenticationHelper, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.companyMapper = companyMapper;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<CompanyOutDTO> getAll(@RequestHeader HttpHeaders headers,
                                      @RequestParam(required = false) Optional<String> search) {
        try {
            authenticationHelper.tryGetUser(headers);
            return companyService.getAll(search).stream()
                    .map(companyMapper::ObjectToDTO)
                    .collect(Collectors.toList());
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CompanyOutDTO getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            authenticationHelper.tryGetUser(headers);
            return companyMapper.ObjectToDTO(companyService.getById(id));
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
