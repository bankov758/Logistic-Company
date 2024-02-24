package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Office;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.office.OfficeCreateDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeOutDto;
import com.nbu.logisticcompany.entities.dtos.office.OfficeUpdateDto;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.mappers.OfficeMapper;
import com.nbu.logisticcompany.services.interfaces.OfficeService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offices")
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
    public List<OfficeOutDto> getAll(@RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return officeService.getAll().stream()
                .map(officeMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OfficeOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return officeMapper.ObjectToDTO(officeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers,
                         @Valid @RequestBody OfficeCreateDto officeCreateDto, BindingResult result) {
        try {
            ValidationUtil.validate(result);
            User creator = authenticationHelper.tryGetUser(headers);
            Office office = officeMapper.DTOtoObject(officeCreateDto);
            officeService.create(office, creator);
            return ResponseEntity.ok().body(officeCreateDto);
            //return office;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                         @Valid @RequestBody OfficeUpdateDto officeUpdateDto, BindingResult result) {
        ValidationUtil.validate(result);
        User updater = authenticationHelper.tryGetUser(headers);
        Office office = officeMapper.UpdateDTOtoOffice(officeUpdateDto);
        officeService.update(office, updater);
        return ResponseEntity.ok().body(officeUpdateDto);
        //return office;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User destroyer = authenticationHelper.tryGetUser(headers);
        officeService.delete(id, destroyer);
    }

}
