package com.nbu.logisticcompany.controllers;

import com.nbu.logisticcompany.controllers.helpers.AuthenticationHelper;
import com.nbu.logisticcompany.entities.Tariff;
import com.nbu.logisticcompany.entities.User;
import com.nbu.logisticcompany.entities.dtos.*;
import com.nbu.logisticcompany.mappers.TariffsMapper;
import com.nbu.logisticcompany.services.interfaces.TariffsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tariffs")
@CrossOrigin(origins = "http://localhost:3000")
public class TariffController {

    private final TariffsService tariffsService;
    private final AuthenticationHelper authenticationHelper;
    private final TariffsMapper tariffsMapper;

    public TariffController(TariffsService tariffsService, AuthenticationHelper authenticationHelper, TariffsMapper tariffsMapper) {
        this.tariffsService = tariffsService;
        this.authenticationHelper = authenticationHelper;
        this.tariffsMapper = tariffsMapper;
    }

    @GetMapping
    public List<TariffOutDto> getAll(@RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return tariffsService.getAll().stream()
                .map(tariffsMapper::ObjectToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TariffOutDto getById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        authenticationHelper.tryGetUser(headers);
        return tariffsMapper.ObjectToDTO(tariffsService.getById(id));
    }

    @PostMapping
    public Tariff create(@Valid @RequestBody TariffCreateDto tariffCreateDto) {
        try {
            Tariff tariff = tariffsMapper.DTOtoObject(tariffCreateDto);
            tariffsService.create(tariff);
            return tariff;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping
    public Tariff update(@RequestHeader HttpHeaders headers,
                         @Valid @RequestBody TariffUpdateDto tariffUpdateDto) {
        User updater = authenticationHelper.tryGetUser(headers);
        Tariff tariff = tariffsMapper.UpdateDTOtoTariffs(tariffUpdateDto);
        tariffsService.update(tariff, updater);
        return tariff;
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        tariffsService.delete(id, user);
    }

}
