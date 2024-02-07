package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariffs;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dto.*;
import com.nbu.logisticcompany.exceptions.DuplicateEntityException;
import com.nbu.logisticcompany.exceptions.EntityNotFoundException;
import com.nbu.logisticcompany.exceptions.UnauthorizedOperationException;
import com.nbu.logisticcompany.mappers.TariffsMapper;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
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
@RequestMapping("/api/tariffs")
public class TariffsController {


    private final TariffsService tariffsService;

    private final AuthenticationHelper authenticationHelper;

    private final TariffsMapper tariffsMapper;

    public TariffsController(TariffsService tariffsService, AuthenticationHelper authenticationHelper, TariffsMapper tariffsMapper) {
        this.tariffsService = tariffsService;
        this.authenticationHelper = authenticationHelper;
        this.tariffsMapper = tariffsMapper;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<TariffsOutDTO> getAll(@RequestHeader HttpHeaders headers){
        try {
            authenticationHelper.tryGetUser(headers);
            return tariffsService.getAll().stream()
                    .map(tariffsMapper::ObjectToDTO)
                    .collect(Collectors.toList());
        } catch (UnauthorizedOperationException u) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, u.getMessage());
        }
    }
    @GetMapping("/{id}")
    public TariffsOutDTO getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {

            authenticationHelper.tryGetUser(headers);
            return tariffsMapper.ObjectToDTO(tariffsService.getById(id));

    }
    @PostMapping
    public Tariffs create(@Valid @RequestBody TariffsCreateDTO tariffsCreateDTO) {
        try {
            Tariffs tariffs = tariffsMapper.DTOtoObject(tariffsCreateDTO);
            tariffsService.create(tariffs);
            return tariffs;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @PutMapping()
    public Tariffs update(@RequestHeader HttpHeaders headers,
                          @Valid @RequestBody TariffsUpdateDTO tariffsUpdateDTO) {

            User updater = authenticationHelper.tryGetUser(headers);
            Tariffs tariffs = tariffsMapper.UpdateDTOtoTariffs(tariffsUpdateDTO);
            tariffsService.update(tariffs, updater);
            return tariffs;

    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        User user = authenticationHelper.tryGetUser(headers);
        tariffsService.delete(id, user);
    }
}
