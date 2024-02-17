package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.*;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.mappers.OfficeMapper;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/office")
@CrossOrigin(origins = "http://localhost:3000")
public class OfficeController {

    private final OfficeService officeService;

    private final AuthenticationHelper authenticationHelper;

    private final OfficeMapper officeMapper;

    public OfficeController(OfficeService officeService, AuthenticationHelper authenticationHelper, OfficeMapper officeMapper) {
        this.officeService = officeService;
        this.authenticationHelper = authenticationHelper;
        this.officeMapper = officeMapper;
    }

    @GetMapping
    public List<OfficeOutDto> getAll(@RequestHeader HttpHeaders headers){
        try {
            authenticationHelper.tryGetUser(headers);
            return officeService.getAll().stream()
                    .map(officeMapper::ObjectToDTO)
                    .collect(Collectors.toList());
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        }
    }

    @GetMapping("/{id}")
    public OfficeOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return officeMapper.ObjectToDTO(officeService.getById(id));
    }

    @PostMapping
    public Office create(@Valid @RequestBody OfficeCreateDto officeCreateDto) {
        try {
            Office office = officeMapper.DTOtoObject(officeCreateDto);
            officeService.create(office);
            return office;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public Office update(@RequestHeader HttpHeaders headers,
                         @Valid @RequestBody OfficeUpdateDto officeUpdateDto) {

        User updater = authenticationHelper.tryGetUser(headers);
        Office office = officeMapper.UpdateDTOtoOffice(officeUpdateDto);
        officeService.update(office, updater);
        return office;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        User user = authenticationHelper.tryGetUser(headers);
        officeService.delete(id, user);
    }
}
